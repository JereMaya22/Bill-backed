package com.gbill.getallfinalconsumerbill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gbill.getallfinalconsumerbill.clients.ValidationService;
import com.gbill.getallfinalconsumerbill.exception.ConnectionFaildAuthenticationException;
import com.gbill.getallfinalconsumerbill.exception.InvalidTokenException;
import com.gbill.getallfinalconsumerbill.exception.InvalidUserException;
import com.gbill.getallfinalconsumerbill.exception.NotFoundException;
import com.gbill.getallfinalconsumerbill.mapper.FinalConsumerBillMapper;
import com.gbill.getallfinalconsumerbill.repository.IBillRepository;

import feign.FeignException;
import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.usersmodule.auth.SimpleUserDto;

import java.util.stream.Collectors;

@Service
public class FinalConsumerBillService implements IFinalConsumerBillService{

    private final IBillRepository billRepository;
    private final ValidationService validationService;

    public FinalConsumerBillService(IBillRepository BillRepository, ValidationService validationService){
        this.billRepository = BillRepository;
        this.validationService = validationService;
    }

    private void validationUser(String token){
        SimpleUserDto user;
        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException("Token is missing or empty.");
        }
        try {
            user = validationService.validationSession(token);
        } catch (FeignException.Unauthorized e) {
            throw new InvalidTokenException("Token is expired or invalid.");
        } catch (FeignException e) {
            throw new ConnectionFaildAuthenticationException("Error communicating with validation service.");
        } catch (Exception e) {
            throw new ConnectionFaildAuthenticationException("An unexpected error occurred during token validation.");
        }

        if (user == null || user.getId() == null) {
            throw new InvalidUserException("Invalid or unauthorized user session.");
        }
    }

    @Override
    public List<ShowBillDto> getAllBill(String token) {

        validationUser(token);

        var bills = billRepository.findAll();
        if (bills.isEmpty()) {
            throw new NotFoundException("List empty");
        }

        return bills.stream()
            .map(FinalConsumerBillMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public ShowBillDto getBygenerationCode(String generationCode, String token) {

        validationUser(token);

        return billRepository.findByGenerationCode(generationCode)
                .map(FinalConsumerBillMapper::toDto)
                .orElseThrow(() -> new NotFoundException(
                    "La factura con el codigo " + generationCode +" no se encontro"));
    }

}
