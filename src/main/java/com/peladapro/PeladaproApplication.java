package com.peladapro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PeladaproApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeladaproApplication.class, args);

	}


}
