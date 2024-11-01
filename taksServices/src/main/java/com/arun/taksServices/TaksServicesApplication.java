package com.arun.taksServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaksServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaksServicesApplication.class, args);
	}

}
