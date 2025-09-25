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
import com.gbill.createfinalconsumerbill.clients.ValidationService;

import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.billmodule.product.ProductBillDTO;
import shareddtos.usersmodule.auth.SimpleUserDto;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.gbill.createfinalconsumerbill.model.BillItem;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{


    private final BillRepository billRepository;
    private final ValidationService validationService;
    private final ProductService productService;

    public FinalConsumerBillService(BillRepository billRepository
        , ValidationService validationService
        , ProductService productService)
    {
        this.billRepository = billRepository;
        this.validationService = validationService;
        this.productService = productService;
    }

    @Override
    public ShowBillDto createFinalConsumerBill(CreateFinalConsumerBillDTO createFinalConsumerBillDTO, String token) {


        SimpleUserDto user;
        try {
            user = validationService.ValidationSession(token);
        } catch (FeignException.Unauthorized e) {
            if (token == null || token.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is missing or empty.");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is expired or invalid.");
            }
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error communicating with validation service.", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during token validation.", e);
        }

        if (user == null || user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or unauthorized user session.");
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
        List<ProductBillDTO> products = productService.getAllIds(productIds);

        //mapeamos los ids
        Map<Long, ProductBillDTO> productMap = products.stream().collect(Collectors.toMap(ProductBillDTO::getId, p -> p));

        List<CreateBillItemDTO> productItem = createFinalConsumerBillDTO.getProducts().stream()
            .map(item -> {

                ProductBillDTO product = productMap.get(item.getProductId());
                double subTotal = product.getPrice() * item.getRequestedQuantity();

                CreateBillItemDTO billitem = new CreateBillItemDTO();
                billitem.setProductId(product.getId());
                billitem.setName(product.getName());
                billitem.setRequestedQuantity(item.getRequestedQuantity());
                billitem.setPrice(product.getPrice());
                billitem.setSubTotal(subTotal); 

                return billitem;
            }).collect(Collectors.toList());
        
            

        // Calculamos los totales
        Double totalWithoutIva = 0.0;
        for (CreateBillItemDTO item : productItem) {
            totalWithoutIva += item.getSubTotal();
        }
        perceivedIva = totalWithoutIva * ivaRate;
        totalWithIva = totalWithoutIva + perceivedIva;

        //convierte en entidad el dto
        FinalConsumerBill bill = FinalConsumerBillMapper.toEntity(createFinalConsumerBillDTO, generationCode
        , controlNumber, date, ivaRate, user.getFirstName() +" "+ user.getLastName(), totalWithIva
        ,productItem);
        
        bill.setNonTaxedSales(createFinalConsumerBillDTO.getNonTaxedSales());
        bill.setExemptSales(createFinalConsumerBillDTO.getExemptSales());
        bill.setTaxedSales(totalWithoutIva);
        bill.setPerceivedIva(perceivedIva);
        bill.setWithheldIva(createFinalConsumerBillDTO.getWithheldIva());
        bill.setTotalWithIva(totalWithIva);

        // Asignar la FinalConsumerBill a cada BillItem
        for (BillItem item : bill.getProducts()) {
            item.setSubTotal(item.getPrice() * item.getRequestedQuantity());
            item.setFinalConsumerBill(bill);
        }
        //guarda la factura
        billRepository.save(bill);

        return FinalConsumerBillMapper.toShowBillDto(bill);
    }

}




