package com.gbill.getallfinalconsumerbill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GetallfinalconsumerbillApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetallfinalconsumerbillApplication.class, args);
	}

}
