package com.gbill.createfinalconsumerbill.service;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.repository.BillRepository;
import org.springframework.stereotype.Service;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{

    private final BillRepository billRepository;

    public FinalConsumerBillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    private FinalConsumerBill convertToEntity(CreateFinalConsumerBillDTO createFinalConsumerBillDTO){
        FinalConsumerBill bill = new FinalConsumerBill(
            null,
            createFinalConsumerBillDTO.getGenerationCode(),
            createFinalConsumerBillDTO.getControlNumber(),
            createFinalConsumerBillDTO.getBillGenerationDate(),
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
