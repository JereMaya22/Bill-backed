package com.gbill.createfinalconsumerbill.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gbill.createfinalconsumerbill.mapper.FinalConsumerBillMapper;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateBillItemRequestDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateReceiver;
import com.gbill.createfinalconsumerbill.modeldto.CreateTransmitter;
import com.gbill.createfinalconsumerbill.repository.BillRepository;
import com.gbill.createfinalconsumerbill.clients.GetProductsByIds;
import com.gbill.createfinalconsumerbill.clients.ValidationService;
import com.gbill.createfinalconsumerbill.exception.ConnectionFaildAuthenticationException;
import com.gbill.createfinalconsumerbill.exception.InvalidTokenException;
import com.gbill.createfinalconsumerbill.exception.InvalidUserException;
import shareddtos.usersmodule.auth.SimpleUserDto;

import shareddtos.billmodule.bill.ShowBillDto;
import com.gbill.createfinalconsumerbill.modeldto.NewProductDTO;
import feign.FeignException;
import com.gbill.createfinalconsumerbill.model.BillItem;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{


    private final BillRepository billRepository;
    private final ValidationService validationService;
    private final GetProductsByIds getProductsByIds;
    private final PdfInvoiceService pdfInvoiceService;

    public FinalConsumerBillService(BillRepository billRepository
        , ValidationService validationService
        , GetProductsByIds getProductsByIds,
        PdfInvoiceService pdfInvoiceService)
    {
        this.billRepository = billRepository;
        this.validationService = validationService;
        this.getProductsByIds = getProductsByIds;
        this.pdfInvoiceService = pdfInvoiceService;
    }

    @Override
    public ShowBillDto createFinalConsumerBill(CreateFinalConsumerBillDTO createFinalConsumerBillDTO, String token) {

        SimpleUserDto user;
        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException("Token is missing or empty.");
        }
        try {
            user = validationService.ValidationSession(token);
        } catch (FeignException.Unauthorized e) {
            throw new InvalidTokenException("Token is expired or invalid.");
        } catch (FeignException e) {
            throw new ConnectionFaildAuthenticationException("Error communicating with validation service.");
        } catch (Exception e) {
            throw new ConnectionFaildAuthenticationException("An unexpected error occurred during token validation.");
        }

        if (user == null || user.getId() == null) {
            throw new InvalidUserException("Invalid or unauthorized user session.");
        }

        if (createFinalConsumerBillDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        // Asegurar que exista un receiver (crear uno por defecto si esta vacio)
        CreateReceiver receiverDto = createFinalConsumerBillDTO.getReceiver();
        if (receiverDto == null) {
            // Crear un receiver por defecto
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
    
        // Verificar y rellenar campos de cliente si están vacíos
        if (receiverDto.getCustomerName() == null || receiverDto.getCustomerName().trim().isEmpty()) {
            receiverDto.setCustomerName("Consumidor Final");
        }
        if (receiverDto.getCustomerLastname() == null) {
            receiverDto.setCustomerLastname("Final");
        }
        if (receiverDto.getCustomerDocument() == null || receiverDto.getCustomerDocument().trim().isEmpty()) {
            receiverDto.setCustomerDocument("99999999-9");
        } else {
            receiverDto.setCustomerDocument(receiverDto.getCustomerDocument().trim());
        }
        if (receiverDto.getCustomerAddress() == null || receiverDto.getCustomerAddress().trim().isEmpty()) {
            receiverDto.setCustomerAddress("Dirección Genérica");
        }
        if (receiverDto.getCustomerEmail() == null || receiverDto.getCustomerEmail().trim().isEmpty()) {
            receiverDto.setCustomerEmail("consumidor@example.com");
        }
        if (receiverDto.getCustomerPhone() == null || receiverDto.getCustomerPhone().trim().isEmpty()) {
            receiverDto.setCustomerPhone("9999-9999");
        } else {
            receiverDto.setCustomerPhone(receiverDto.getCustomerPhone().trim());
        }

        //genera el codigo
        String generationCode = UUID.randomUUID().toString();

        //genera el numero de control
        String controlNumber = "DTE-03" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) 
                + "-" + ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L, 
                    10_000_000_000_000_000L);

        //Genera la fecha de creacion de la factura                                                
        LocalDateTime date = LocalDateTime.now().withNano(0);

        //variables 
        Double ivaRate = 0.13;
        Double perceivedIva;
        Double totalWithIva;


        //obtenemos la lista de ids de productos
        List<Long> productIds = createFinalConsumerBillDTO.getProducts().stream()
            .map(CreateBillItemRequestDTO::getProductId)
            .collect(Collectors.toList());

        //obtenemos los productos uno por uno del nuevo servicio
        List<NewProductDTO> products = productIds.stream()
            .map(id -> {
                try {
                    return getProductsByIds.getById(id);
                } catch (Exception e) {
                    // Manejar error si el producto no existe
                    throw new RuntimeException("Error obteniendo producto con ID: " + id, e);
                }
            })
            .collect(Collectors.toList());

        //mapeamos los ids
        Map<Long, NewProductDTO> productMap = products.stream().collect(Collectors.toMap(NewProductDTO::getProductoId, p -> p));


        //mapeamos los productos para pasarlos a una lista de CreateBillItemDTO
        List<CreateBillItemDTO> productItem = createFinalConsumerBillDTO.getProducts().stream()
            .map(item -> {

                NewProductDTO product = productMap.get(item.getProductId());
                double subTotal = new BillItem().sumSubtotal(product.getPrecio(),item.getRequestedQuantity() );
                
                CreateBillItemDTO billitem = new CreateBillItemDTO();
                billitem.setProductId(product.getProductoId());
                billitem.setName(product.getNombre());
                billitem.setRequestedQuantity(item.getRequestedQuantity());
                billitem.setPrice(product.getPrecio());
                billitem.setSubTotal(subTotal); 

                return billitem;
            }).collect(Collectors.toList());
        
            

        // calculo de totales
        Double totalWithoutIva = 0.0;
        for (CreateBillItemDTO item : productItem) {
            totalWithoutIva += item.getSubTotal();
        }
        perceivedIva = totalWithoutIva * ivaRate;
        totalWithIva = totalWithoutIva + perceivedIva;

        var transmitterDto = createFinalConsumerBillDTO.getTransmitter();
        if (transmitterDto.getCompanyName() == null || transmitterDto.getCompanyName().trim().isEmpty()) {
            transmitterDto.setCompanyName("Becky's Florist S.A. de C.V.");
        }
        if (transmitterDto.getCompanyDocument() == null || transmitterDto.getCompanyDocument().trim().isEmpty()) {
            transmitterDto.setCompanyDocument("12345678-9");
        }
        if (transmitterDto.getCompanyAddress() == null || transmitterDto.getCompanyAddress().trim().isEmpty()) {
            transmitterDto.setCompanyAddress("Av. Las Flores #123, San Salvador");
        }
        if (transmitterDto.getCompanyEmail() == null || transmitterDto.getCompanyEmail().trim().isEmpty()) {
            transmitterDto.setCompanyEmail("facturacion@beckysflorist.com");
        }
        if (transmitterDto.getCompanyPhone() == null || transmitterDto.getCompanyPhone().trim().isEmpty()) {
            transmitterDto.setCompanyPhone("2212-3456");
        }

        //conversion de dto a entidad
        FinalConsumerBill bill = FinalConsumerBillMapper.toEntity(
            createFinalConsumerBillDTO,
            generationCode,
            controlNumber,
            date,
            ivaRate,
            user.getFirstName() +" "+ user.getLastName(),
            0.0,
            0.0,
            totalWithoutIva, 
            perceivedIva,
            totalWithIva,
            productItem,
            null
        );


        // A cada billitem asignamos su relacion con finalconsumer
        for (BillItem item : bill.getProducts()) {
            item.setSubTotal(item.getPrice() * item.getRequestedQuantity());
            item.setFinalConsumerBill(bill);
        }

        //guardamos la factura
        billRepository.save(bill);

        //disminucion de inventario
        for(BillItem item : bill.getProducts()){
            try{
                NewProductDTO updatedProduct = getProductsByIds.decreaseStock(item.getProductId(), item.getRequestedQuantity());
                System.out.println("Stock disminuido exitosamente para producto " + item.getProductId() + 
                                  ". Stock actual: " + updatedProduct.getStockMaximo());
            }catch(Exception e){
                System.err.println("Error disminuyendo stock para producto " + item.getProductId() + ": " + e.getMessage());
            }
        }

        try {

            Path pdfPath = pdfInvoiceService.generateInvoicePdf(bill);
            if(pdfPath != null) {
                bill.setPdfPath(pdfPath.toString());
                billRepository.save(bill);
            }
        }catch (Exception ignored) {}

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