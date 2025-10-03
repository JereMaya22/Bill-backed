package com.gbill.createfinalconsumerbill.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gbill.createfinalconsumerbill.mapper.FinalConsumerBillMapper;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateBillItemRequestDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.repository.BillRepository;
import com.gbill.createfinalconsumerbill.clients.GetProductsByIds;
import com.gbill.createfinalconsumerbill.clients.ValidationService;
import com.gbill.createfinalconsumerbill.exception.ConnectionFaildAuthenticationException;
import com.gbill.createfinalconsumerbill.exception.InvalidTokenException;
import com.gbill.createfinalconsumerbill.exception.InvalidUserException;

import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.inventorymodule.ShowProductDTO;
import shareddtos.usersmodule.auth.SimpleUserDto;
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
    
        // Verificar y rellenar campos de cliente si están vacíos
        if (createFinalConsumerBillDTO.getCustomerName() == null || createFinalConsumerBillDTO.getCustomerName().trim().isEmpty()) {
            createFinalConsumerBillDTO.setCustomerName("Consumidor Final");
        }
        if (createFinalConsumerBillDTO.getCustomerDocument() == null || createFinalConsumerBillDTO.getCustomerDocument().trim().isEmpty()) {
            createFinalConsumerBillDTO.setCustomerDocument("99999999-9");
        }
        if (createFinalConsumerBillDTO.getCustomerAddress() == null || createFinalConsumerBillDTO.getCustomerAddress().trim().isEmpty()) {
            createFinalConsumerBillDTO.setCustomerAddress("Dirección Genérica");
        }
        if (createFinalConsumerBillDTO.getCustomerEmail() == null || createFinalConsumerBillDTO.getCustomerEmail().trim().isEmpty()) {
            createFinalConsumerBillDTO.setCustomerEmail("consumidor@example.com");
        }
        if (createFinalConsumerBillDTO.getCustomerPhone() == null || createFinalConsumerBillDTO.getCustomerPhone().trim().isEmpty()) {
            createFinalConsumerBillDTO.setCustomerPhone("0000-0000"); 
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

        //obtenemso los productos
        List<ShowProductDTO> products = getProductsByIds.getByIds(productIds);

        //mapeamos los ids
        Map<Long, ShowProductDTO> productMap = products.stream().collect(Collectors.toMap(ShowProductDTO::getId, p -> p));

        //mapeamos los productos para pasarlos a una lista de CreateBillItemDTO
        List<CreateBillItemDTO> productItem = createFinalConsumerBillDTO.getProducts().stream()
            .map(item -> {

                ShowProductDTO product = productMap.get(item.getProductId());
                double subTotal = product.getPrice() * item.getRequestedQuantity();

                CreateBillItemDTO billitem = new CreateBillItemDTO();
                billitem.setProductId(product.getId());
                billitem.setName(product.getName());
                billitem.setRequestedQuantity(item.getRequestedQuantity());
                billitem.setPrice(product.getPrice());
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

        //conversion de dto a entidad
        FinalConsumerBill bill = FinalConsumerBillMapper.toEntity(
            createFinalConsumerBillDTO,
            generationCode,
            controlNumber,
            date,
            ivaRate,
            user.getFirstName() +" "+ user.getLastName(),
            "Becky's Florist S.A. de C.V.",
            "0614-987654-101-3",
            "Av. Las Flores #123, San Salvador",
            "facturacion@beckysflorist.com",
            "2222-3333",
            0.0,
            0.0,
            totalWithoutIva, 
            perceivedIva,
            totalWithIva,
            productItem
        );


        // A cada billitem asignamos su relacion con finalconsumer
        for (BillItem item : bill.getProducts()) {
            item.setSubTotal(item.getPrice() * item.getRequestedQuantity());
            item.setFinalConsumerBill(bill);
        }

        //guardamos la factura
        billRepository.save(bill);

        try {
            pdfInvoiceService.generateInvoicePdf(bill);
        }catch (Exception ignored) {}

        return FinalConsumerBillMapper.toShowBillDto(bill);
    }

}




