package com.gbill.createfinalconsumerbill.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gbill.createfinalconsumerbill.mapper.FinalConsumerBillMapper;
import com.gbill.createfinalconsumerbill.model.*;
import com.gbill.createfinalconsumerbill.repository.BillRepository;

import com.gbill.createfinalconsumerbill.clients.*;
import com.gbill.createfinalconsumerbill.modeldto.*;
import com.gbill.createfinalconsumerbill.modeldto.payment.*;
import com.gbill.createfinalconsumerbill.modeldto.promortion.*;

import com.gbill.createfinalconsumerbill.exception.*;
import com.gbill.createfinalconsumerbill.service.payment.PaymentService;

import feign.FeignException;
import shareddtos.usersmodule.auth.SimpleUserDto;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;
import shareddtos.billmodule.bill.ShowBillDto;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService {

    private final BillRepository billRepository;
    private final ValidationService validationService;
    private final GetProductsByIds getProductsByIds;
    private final PdfInvoiceService pdfInvoiceService;
    private final PromotionClient promotionClient;
    private final PaymentService paymentService;

    public FinalConsumerBillService(
        BillRepository billRepository,
        ValidationService validationService,
        GetProductsByIds getProductsByIds,
        PdfInvoiceService pdfInvoiceService,
        PromotionClient promotionClient,
        PaymentService paymentService
    ) {
        this.billRepository = billRepository;
        this.validationService = validationService;
        this.getProductsByIds = getProductsByIds;
        this.pdfInvoiceService = pdfInvoiceService;
        this.promotionClient = promotionClient;
        this.paymentService = paymentService;
    }

    @Override
    public ShowBillDto createFinalConsumerBill(CreateFinalConsumerBillDTO createFinalConsumerBillDTO, String token) {

        // === Validación del token ===
        SimpleUserDto user;
        if (token == null || token.isEmpty())
            throw new InvalidTokenException("Token is missing or empty.");
        try {
            user = validationService.ValidationSession(token);
        } catch (FeignException.Unauthorized e) {
            throw new InvalidTokenException("Token is expired or invalid.");
        } catch (FeignException e) {
            throw new ConnectionFaildAuthenticationException("Error communicating with validation service.");
        } catch (Exception e) {
            throw new ConnectionFaildAuthenticationException("An unexpected error occurred during token validation.");
        }

        if (user == null || user.getId() == null)
            throw new InvalidUserException("Invalid or unauthorized user session.");

        if (createFinalConsumerBillDTO == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");

        // === Default receiver and transmitter ===
        CreateReceiver receiverDto = createFinalConsumerBillDTO.getReceiver();
        if (receiverDto == null) {
            receiverDto = new CreateReceiver();
            receiverDto.setCustomerName("Consumidor Final");
            receiverDto.setCustomerLastname("Final");
            receiverDto.setCustomerDocument("99999999-9");
            receiverDto.setCustomerAddress("Dirección Genérica");
            receiverDto.setCustomerEmail("consumidor@example.com");
            receiverDto.setCustomerPhone("9999-9999");
            createFinalConsumerBillDTO.setReceiver(receiverDto);
        }

        if (createFinalConsumerBillDTO.getTransmitter() == null) {
            CreateTransmitter defaultTransmitter = new CreateTransmitter();
            defaultTransmitter.setCompanyName("Becky's Florist S.A. de C.V.");
            defaultTransmitter.setCompanyDocument("12345678-9");
            defaultTransmitter.setCompanyAddress("Av. Las Flores #123, San Salvador");
            defaultTransmitter.setCompanyEmail("facturacion@beckysflorist.com");
            defaultTransmitter.setCompanyPhone("2212-3456");
            createFinalConsumerBillDTO.setTransmitter(defaultTransmitter);
        }

        // === Generate metadata ===
        String generationCode = UUID.randomUUID().toString();
        String controlNumber = "DTE-03" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-" + ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L, 10_000_000_000_000_000L);
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Double ivaRate = 0.13;

        // === Get product info ===
        List<Long> productIds = createFinalConsumerBillDTO.getProducts().stream()
                .map(CreateBillItemRequestDTO::getProductId)
                .collect(Collectors.toList());

        Map<Long, NewProductDTO> productMap = productIds.stream()
                .map(id -> {
                    try {
                        return getProductsByIds.getById(id);
                    } catch (Exception e) {
                        throw new RuntimeException("Error obteniendo producto con ID: " + id, e);
                    }
                })
                .collect(Collectors.toMap(NewProductDTO::getProductoId, p -> p));

        List<CreateBillItemDTO> productItem = createFinalConsumerBillDTO.getProducts().stream()
                .map(item -> {
                    NewProductDTO product = productMap.get(item.getProductId());
                    double subTotal = new BillItem().sumSubtotal(product.getPrecio(), item.getRequestedQuantity());

                    CreateBillItemDTO dto = new CreateBillItemDTO();
                    dto.setProductId(product.getProductoId());
                    dto.setName(product.getNombre());
                    dto.setRequestedQuantity(item.getRequestedQuantity());
                    dto.setPrice(product.getPrecio());
                    dto.setSubTotal(subTotal);
                    return dto;
                }).collect(Collectors.toList());

        // === PROMOCIONES ===
        Boolean promotionApplied = false;
        String promotionCode = null;
        String promotionName = null;
        Double totalPromotionDiscount = 0.0;
        List<Long> productsWithPromotion = new ArrayList<>();

        for (CreateBillItemDTO item : productItem) {
            try {
                PromotionAvailableRequest promoRequest = new PromotionAvailableRequest();
                promoRequest.setAccount(user.getUsername() + " " + user.getLastName());
                promoRequest.setProductos(List.of(new ProductPromotionDTO(
                        item.getProductId(),
                        item.getRequestedQuantity(),
                        item.getPrice(),
                        item.getName()
                )));

                PromotionAvailableResponse availablePromos = promotionClient.getAvailablePromotions(promoRequest);

                if (availablePromos != null && availablePromos.getPromocionesDisponibles() != null
                        && !availablePromos.getPromocionesDisponibles().isEmpty()) {

                    PromotionDetailDTO bestPromo = availablePromos.getPromocionesDisponibles().stream()
                            .max(Comparator.comparingDouble(p -> p.getDescuentoEstimado() != null ? p.getDescuentoEstimado() : 0.0))
                            .orElse(null);

                    if (bestPromo != null) {
                        ApplyPromotionRequest applyRequest = new ApplyPromotionRequest();
                        applyRequest.setAccount(user.getUsername() + " " + user.getLastName());
                        applyRequest.setProductos(promoRequest.getProductos());
                        applyRequest.setCodigoPromocion(bestPromo.getCodigo());

                        ApplyPromotionResponse applied = promotionClient.applyPromotion(applyRequest);

                        if (applied.getPromocionAplicada() != null && applied.getPromocionAplicada()) {
                            promotionApplied = true;
                            promotionCode = bestPromo.getCodigo();
                            promotionName = bestPromo.getNombre();
                            totalPromotionDiscount += applied.getMontoDescuento();
                            productsWithPromotion.add(item.getProductId());

                            applied.getProductosConDescuento().forEach(discounted -> {
                                if (discounted.getId().equals(item.getProductId())) {
                                    item.setPrice(discounted.getPrecioConDescuento() / discounted.getQuantity());
                                    item.setSubTotal(discounted.getPrecioConDescuento());
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("No se pudo aplicar promoción a " + item.getName() + ": " + e.getMessage());
            }
        }

        // === Totals ===
        Double totalWithoutIva = productItem.stream()
                .mapToDouble(CreateBillItemDTO::getSubTotal)
                .sum();

        Double perceivedIva = totalWithoutIva * ivaRate;
        Double totalWithIva = totalWithoutIva + perceivedIva;

        // === Build entity ===
        FinalConsumerBill bill = FinalConsumerBillMapper.toEntity(
                createFinalConsumerBillDTO,
                generationCode,
                controlNumber,
                date,
                ivaRate,
                user.getFirstName() + " " + user.getLastName(),
                0.0,
                0.0,
                totalWithoutIva,
                perceivedIva,
                totalWithIva,
                productItem,
                null,
                promotionApplied,
                promotionCode,
                promotionName,
                totalPromotionDiscount,
                productsWithPromotion
        );

        bill.getProducts().forEach(i -> {
            i.setSubTotal(i.getPrice() * i.getRequestedQuantity());
            i.setFinalConsumerBill(bill);
        });

        // === INTEGRACIÓN DEL PAGO ===
        PaymentDTO paymentDto = createFinalConsumerBillDTO.getPayment();
        String paymentCondition = createFinalConsumerBillDTO.getPaymentCondition();

        if (paymentDto == null) {
            paymentDto = new PaymentDTO();
        }

        // 🔹 Si no viene paymentType, se hereda del paymentCondition
        if (paymentDto.getPaymentType() == null && paymentCondition != null) {
            paymentDto.setPaymentType(paymentCondition.toUpperCase());
        }

        // === EFECTIVO ===
        if ("EFECTIVO".equalsIgnoreCase(paymentDto.getPaymentType())) {
            Payment payment = new Payment();
            payment.setPaymentType("EFECTIVO");
            payment.setBill(bill);
            bill.setPayment(payment);
        }

        // === TARJETA ===
        else if ("TARJETA".equalsIgnoreCase(paymentDto.getPaymentType())) {
            CardValidationRequest validationReq = new CardValidationRequest(
                paymentDto.getMaskedCardNumber(),
                paymentDto.getCardHolder(),
                null,
                null
            );

            CardValidationResponse validation = paymentService.validateCard(validationReq);

            if (!validation.isValid()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validation.getMessage());
            }

            Payment payment = new Payment();
            payment.setPaymentType("TARJETA");
            payment.setCardType(validation.getCardType());
            payment.setMaskedCardNumber(validation.getMaskedCardNumber());
            payment.setCardHolder(paymentDto.getCardHolder());
            payment.setAuthorizationCode(validation.getAuthorizationCode());
            payment.setBill(bill);
            bill.setPayment(payment);
        }

        // === Save bill ===
        billRepository.save(bill);

        // === Decrease stock ===
        for (BillItem item : bill.getProducts()) {
            try {
                NewProductDTO updatedProduct = getProductsByIds.decreaseStock(item.getProductId(), item.getRequestedQuantity());
                System.out.println("Stock disminuido para producto " + item.getProductId() + ": " + updatedProduct.getStockMaximo());
            } catch (Exception e) {
                System.err.println("Error disminuyendo stock: " + e.getMessage());
            }
        }

        // === Generate PDF ===
        try {
            Path pdfPath = pdfInvoiceService.generateInvoicePdf(bill);
            if (pdfPath != null) {
                bill.setPdfPath(pdfPath.toString());
                billRepository.save(bill);
            }
        } catch (Exception ignored) {}

        return FinalConsumerBillMapper.toShowBillDto(bill);
    }

    public Optional<byte[]> getPdfByGenerationCode(String generationCode) {
        return billRepository.findBygenerationCode(generationCode)
                .flatMap(bill -> {
                    try {
                        if (bill.getPdfPath() == null) return Optional.empty();
                        Path path = Path.of(bill.getPdfPath());
                        if (!Files.exists(path)) return Optional.empty();
                        return Optional.of(Files.readAllBytes(path));
                    } catch (Exception e) {
                        return Optional.empty();
                    }
                });
    }
}
