package com.gbill.createfiscalcredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CreateFiscalCreditApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreateFiscalCreditApplication.class, args);
    }
}
