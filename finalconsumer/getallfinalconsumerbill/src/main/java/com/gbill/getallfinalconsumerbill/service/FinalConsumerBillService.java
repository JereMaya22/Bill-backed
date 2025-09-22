package com.gbill.getallfinalconsumerbill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gbill.getallfinalconsumerbill.mapper.FinalConsumerBillMapper;
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
            .map(FinalConsumerBillMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public ShowBillDto getBygenerationCode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBygenerationCode'");
    }

    @Override
    public ShowBillDto getBycontrolNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBycontrolNumber'");
    }

}
