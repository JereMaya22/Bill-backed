package com.gbill.getallbill.service;

import com.gbill.getallbill.model.Bill;
import com.gbill.getallbill.modeldto.BillAllDTO;
import com.gbill.getallbill.repository.IBillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService implements IBillService {

    private final IBillRepository billRepository;

    public BillService(IBillRepository billRepository) {
        this.billRepository = billRepository;
    }

    private BillAllDTO convertToDto(Bill bill){

        return new BillAllDTO(
                bill.getCodeGenerator(),
                bill.getDate(),
                bill.getCompanyName(),
                bill.getCompanyNIT(),
                bill.getCompanyAddress(),
                bill.getCompanyPhone(),
                bill.getCompanyEmail(),
                bill.getClientName(),
                bill.getClientDUI(),
                bill.getClientPhone(),
                bill.getClientEmail(),
                bill.getListproducts()
        );
    }

    @Override
    public List<BillAllDTO> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(this::convertToDto).toList();
    }
}
