package com.gbill.getallfinalconsumerbill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gbill.getallfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.getallfinalconsumerbill.repository.IBillRepository;

import shareddtos.billmodule.ShowBillDto;
import java.util.stream.Collectors;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{

    private final IBillRepository billRepository;

    public FinalConsumerBillService(IBillRepository BillRepository){
        this.billRepository = BillRepository;
    }

    @Override
    public List<ShowBillDto> getAllBill() {
        return billRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private ShowBillDto convertToDto(FinalConsumerBill bill) {
        ShowBillDto dto = new ShowBillDto();
        dto.setGenerationCode(bill.getGenerationCode());
        dto.setControlNumber(bill.getControlNumber());
        dto.setBillGenerationDate(bill.getBillGenerationDate());
        dto.setAccount(bill.getAccount());
        dto.setPaymentCondition(bill.getPaymentCondition());
        dto.setCompanyName(bill.getCompanyName());
        dto.setCompanyDocument(bill.getCompanyDocument());
        dto.setCompanyAddress(bill.getCompanyAddress());
        dto.setCompanyEmail(bill.getCompanyEmail());
        dto.setCompanyPhone(bill.getCompanyPhone());
        dto.setCustomerName(bill.getCustomerName());
        dto.setCustomerDocument(bill.getCustomerDocument());
        dto.setCustomerAddress(bill.getCustomerAddress());
        dto.setCustomerEmail(bill.getCustomerEmail());
        dto.setCustomerPhone(bill.getCustomerPhone());
        // Mapear productos si ShowBillDto los contiene
        if (bill.getProducts() != null) {
            dto.setProducts(bill.getProducts().stream()
                .map(productBill -> {
                    shareddtos.billmodule.ProductBillDTO productDto = new shareddtos.billmodule.ProductBillDTO();
                    productDto.setId(productBill.getId());
                    productDto.setName(productBill.getName());
                    productDto.setQuantity(productBill.getQuantity());
                    productDto.setPrice(productBill.getPrice()); 
                    return productDto;
                })
                .collect(Collectors.toList()));
        }
        dto.setNonTaxedSales(bill.getNonTaxedSales());
        dto.setExemptSales(bill.getExemptSales());
        dto.setTaxedSales(bill.getTaxedSales());
        dto.setIva(bill.getIva());
        dto.setPerceivedIva(bill.getPerceivedIva());
        dto.setWithheldIva(bill.getWithheldIva());
        dto.setTotalWithIva(bill.getTotalWithIva());
        return dto;
    }
}
