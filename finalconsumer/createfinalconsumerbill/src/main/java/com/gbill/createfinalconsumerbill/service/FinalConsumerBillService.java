package com.gbill.createfinalconsumerbill.service;

import com.gbill.createfinalconsumerbill.mapper.FinalConsumerBillMapper;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.model.ProductBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.repository.BillRepository;
import com.gbill.createfinalconsumerbill.repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{

    private final BillRepository billRepository;
    private final ProductRepository productRepository;

    public FinalConsumerBillService(BillRepository billRepository, ProductRepository productRepository){
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        
    }
    
    @Override
    public void createFinalConsumerBill(CreateFinalConsumerBillDTO dto) {

        //genera el codigo
        String generationCode = Optional.ofNullable(dto.getGenerationCode())
        .filter(s -> !s.isBlank())
        .orElse(UUID.randomUUID().toString());

        //genera el numero de control
        String controlNumber = Optional.ofNullable(dto.getControlNumber())
                .filter(s -> !s.isBlank())
                .orElse("DTE-03" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) 
                        + "-" + ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L, 
                            10_000_000_000_000_000L));

        //Genera la fecha de creacion de la factura                                                
        LocalDateTime date = LocalDateTime.now().withNano(0);

        //convierte en entidad el dto
        FinalConsumerBill bill = FinalConsumerBillMapper.toEntity(dto, generationCode, controlNumber, date, 0.13);

        //mapea los productos
        List<ProductBill> products = dto.getProducts().stream()
                .map(p -> productRepository.findById(p.getId())
                        .orElseThrow(() -> new RuntimeException("product not found" + p.getId())))
                .peek(prod -> prod.setFinalConsumerBill(bill))
                .toList();

        //agrega los productos a la factura
        bill.setProducts(products);


        //guarda la factura
        billRepository.save(bill);
    }

}
