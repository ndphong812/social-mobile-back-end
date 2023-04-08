package com.backend.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MobilenetworkApplication {

	public static void main(String[] args) {SpringApplication.run(MobilenetworkApplication.class, args);
	}

}
