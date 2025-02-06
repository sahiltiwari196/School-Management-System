package com.smsystem.smsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.smsystem*")
@EntityScan(basePackages = "com.smsystem.model")
@ComponentScan(basePackages = "com.smsytem.securityFilter")
@EnableJpaRepositories("com.smsystem.repository")
public class SmsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsystemApplication.class, args);
	}

}
