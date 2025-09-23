package com.gbill.createfinalconsumerbill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CreatefinalconsumerbillApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatefinalconsumerbillApplication.class, args);
	}

}
