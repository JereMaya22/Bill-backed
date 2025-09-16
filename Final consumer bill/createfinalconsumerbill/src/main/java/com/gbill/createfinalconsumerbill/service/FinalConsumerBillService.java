package com.gbill.createfinalconsumerbill.service;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.repository.BillRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{

    private final BillRepository billRepository;

    public FinalConsumerBillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    private FinalConsumerBill convertToEntity(CreateFinalConsumerBillDTO createFinalConsumerBillDTO){
        String generationCode = (createFinalConsumerBillDTO.getGenerationCode()== null || createFinalConsumerBillDTO.getGenerationCode().isBlank())
            ? UUID.randomUUID().toString() : createFinalConsumerBillDTO.getGenerationCode();

        String  controlNumber = (createFinalConsumerBillDTO.getControlNumber() == null || createFinalConsumerBillDTO.getControlNumber().isBlank())
            ?"DTE-03"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"-"+ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L, 10_000_000_000_000_000L) : createFinalConsumerBillDTO.getControlNumber();

        LocalDateTime date = LocalDateTime.now().withNano(0);

        FinalConsumerBill bill = new FinalConsumerBill(
            null,
            generationCode,
            controlNumber,
            date,
            createFinalConsumerBillDTO.getAccount(),
            createFinalConsumerBillDTO.getPaymentCondition(),
            createFinalConsumerBillDTO.getCompanyName(),
            createFinalConsumerBillDTO.getCompanyDocument(),
            createFinalConsumerBillDTO.getCompanyAddress(),
            createFinalConsumerBillDTO.getCompanyEmail(),
            createFinalConsumerBillDTO.getCompanyPhone(),
            createFinalConsumerBillDTO.getCustomerName(),
            createFinalConsumerBillDTO.getCustomerDocument(),
            createFinalConsumerBillDTO.getCustomerAddress(),
            createFinalConsumerBillDTO.getCustomerEmail(),
            createFinalConsumerBillDTO.getCustomerPhone(),
            createFinalConsumerBillDTO.getProducts(), // por ahora, asignamos después
            createFinalConsumerBillDTO.getNonTaxedSales(),
            createFinalConsumerBillDTO.getExemptSales(),
            createFinalConsumerBillDTO.getTaxedSales(),
            createFinalConsumerBillDTO.getIva(),
            createFinalConsumerBillDTO.getPerceivedIva(),
            createFinalConsumerBillDTO.getWithheldIva(),
            createFinalConsumerBillDTO.getTotalWithIva()
        );
    
        return bill;
    }
    

    @Override
    public void createFinalConsumerBill(CreateFinalConsumerBillDTO bill) {
        billRepository.save(convertToEntity(bill));
    }

}
