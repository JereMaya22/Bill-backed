package com.gbill.getallfinalconsumerbill.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import shareddtos.usersmodule.auth.SimpleUserDto;

@FeignClient(name = "validation-service", url="${app.url.validation}")
public interface ValidationService {

    @GetMapping("header")
    SimpleUserDto validationSession(@RequestHeader(value = "Authorization", required = true) String token); 
}
