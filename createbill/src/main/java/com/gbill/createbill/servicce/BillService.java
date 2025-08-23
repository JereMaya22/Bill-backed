package com.gbill.createbill.servicce;

import com.gbill.createbill.model.Bill;
import com.gbill.createbill.modeldto.CreateBillDto;
import com.gbill.createbill.repository.IBillRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class BillService implements IBillService {

    private final IBillRepository billRepository;

    public BillService(IBillRepository billRepository){
        this.billRepository = billRepository;
    }

    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    private Bill convertToModel(CreateBillDto createBillDto){
        return new Bill(
                null,
                generateCode(8), // genera el código automático de 8 caracteres
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
