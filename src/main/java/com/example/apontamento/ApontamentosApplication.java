package com.example.apontamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ApontamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApontamentosApplication.class, args);
	}

}
