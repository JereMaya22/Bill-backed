package com.gbill.getallfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;

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
    public Optional<ShowBillDto> getBygenerationCode(String generationCode) {
        
        return billRepository.findByGenerationCode(generationCode)
            .map(FinalConsumerBillMapper::toDto);
    }

}
