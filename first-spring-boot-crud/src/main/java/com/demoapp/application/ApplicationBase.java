package com.demoapp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.demoapp" }, exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories("com.demoapp")
@EntityScan(basePackages = { "com.demoapp" })
public class ApplicationBase {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBase.class, args);
	}
}
