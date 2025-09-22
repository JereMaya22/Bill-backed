package com.gbill.getallfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gbill.getallfinalconsumerbill.exception.NotFoundException;
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

        if(billRepository.findAll().isEmpty() || billRepository.findAll() == null){
            throw new NotFoundException("List empty");
        }

        return billRepository.findAll().stream()
            .map(FinalConsumerBillMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ShowBillDto> getBygenerationCode(String generationCode) {
        
        if(billRepository.findByGenerationCode(generationCode).isEmpty() || billRepository.findByGenerationCode(generationCode) == null){
            throw new NotFoundException("La factura con el codgio " + generationCode + " no se encontro.");
        }

        return billRepository.findByGenerationCode(generationCode)
            .map(FinalConsumerBillMapper::toDto);
    }

}
