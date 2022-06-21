package com.example.primermicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PrimermicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimermicroApplication.class, args);
	}

}
