package com.gbill.getallbill.service;

import com.gbill.getallbill.model.Bill;
import com.gbill.getallbill.model.Product;
import com.gbill.getallbill.modeldto.BillAllDTO;
import com.gbill.getallbill.modeldto.ShowProductDto;
import com.gbill.getallbill.repository.IBillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {

    private final IBillRepository billRepository;

    public BillService(IBillRepository billRepository) {
        this.billRepository = billRepository;
    }

    private BillAllDTO convertToDto(Bill bill) {
        if (bill == null) {
            return null;
        }

        BillAllDTO dto = new BillAllDTO();
        dto.setCodeGenerator(bill.getCodeGenerator());
        dto.setDate(bill.getDate());
        dto.setCompanyName(bill.getCompanyName());
        dto.setCompanyNIT(bill.getCompanyNIT());
        dto.setCompanyAddress(bill.getCompanyAddress());
        dto.setCompanyPhone(bill.getCompanyPhone());
        dto.setCompanyEmail(bill.getCompanyEmail());
        dto.setClientName(bill.getClientName());
        dto.setClientDUI(bill.getClientDUI());
        dto.setClientPhone(bill.getClientPhone());
        dto.setClientEmail(bill.getClientEmail());
        
        // Convertir los productos a DTOs
        if (bill.getListproducts() != null) {
            List<ShowProductDto> productDtos = bill.getListproducts().stream()
                    .map(this::convertProductToDto)
                    .collect(Collectors.toList());
            dto.setListproducts(productDtos);
        }
        
        return dto;
    }
    
    private ShowProductDto convertProductToDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ShowProductDto(product.getName(), product.getPrice());
    }

    @Override
    public List<BillAllDTO> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}