package com.gbill.getIdBill.service;

import com.gbill.getIdBill.model.Bill;
import com.gbill.getIdBill.modeldto.ShowBill;
import com.gbill.getIdBill.repository.IBillRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceBill implements IBillService{

    private ShowBill convertToShowBill(Bill bill) {
        ShowBill showBill = new ShowBill();
        showBill.setCodeGenerator(bill.getCodeGenerator());
        showBill.setDate(bill.getDate());

        // Emisor
        showBill.setCompanyName(bill.getCompanyName());
        showBill.setCompanyNIT(bill.getCompanyNIT());
        showBill.setCompanyAddress(bill.getCompanyAddress());
        showBill.setCompanyPhone(bill.getCompanyPhone());
        showBill.setCompanyEmail(bill.getCompanyEmail());

        // Receptor
        showBill.setClientName(bill.getClientName());
        showBill.setClientDUI(bill.getClientDUI());
        showBill.setClientPhone(bill.getClientPhone());
        showBill.setClientEmail(bill.getClientEmail());

        // Products
        showBill.setListproducts(bill.getListproducts());

        return showBill;
    }


    private final IBillRepository billRepository;

    public ServiceBill(IBillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Optional<ShowBill> getById(String codeGenerator) {
        Optional<Bill> billOptional = billRepository.findByCodeGenerator(codeGenerator);

        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            ShowBill showBill = convertToShowBill(bill);
            return Optional.of(showBill);
        }

        return Optional.empty();

    }
}
