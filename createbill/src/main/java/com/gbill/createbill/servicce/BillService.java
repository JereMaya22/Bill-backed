package com.gbill.createbill.servicce;

import com.gbill.createbill.model.Bill;
import com.gbill.createbill.modeldto.CreateBillDto;
import com.gbill.createbill.repository.IBillRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {

    private final IBillRepository billRepository;

    public BillService(IBillRepository billRepository){
        this.billRepository = billRepository;
    }

    private Bill convertToModel(CreateBillDto createBillDto){
        return new Bill(
                null,
                createBillDto.getCodeGenerator(),
                createBillDto.getDate(),
                createBillDto.getCompanyName(),
                createBillDto.getCompanyNIT(),
                createBillDto.getCompanyAddress(),
                createBillDto.getCompanyPhone(),
                createBillDto.getCompanyEmail(),
                createBillDto.getClientName(),
                createBillDto.getClientDUI(),
                createBillDto.getClientPhone(),
                createBillDto.getClientEmail(),
                createBillDto.getListproducts()
        );
    }

    @Override
    public void createBill(CreateBillDto createBillDto) {
        Bill bill = convertToModel(createBillDto);
        billRepository.save(bill);

    }
}
