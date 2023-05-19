package com.demoapp.application;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.demoapp.application.FileStorageService;


@SpringBootApplication(scanBasePackages = { "com.demoapp" }, exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories("com.demoapp")
@EntityScan(basePackages = { "com.demoapp" })
public class ApplicationBase implements CommandLineRunner {
	
	 @Resource
	 FileStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBase.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		 //storageService.deleteAll();
		 //storageService.init();
	}
}
