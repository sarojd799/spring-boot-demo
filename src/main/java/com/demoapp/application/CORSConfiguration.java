package com.demoapp.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration {
	
	private String[] allowedIP = {"http://localhost:4200","http://192.168.0.109:4200"};
	
	private String[] globalIp = {"*"};
	
	private String[] allowedHTTPMethods = {"POST", "GET", "DELETE", "PUT", "OPTIONS"};
	
	@Bean
	@SuppressWarnings("deprecation")	
    public WebMvcConfigurer corsConfigurer() {
        
		return new WebMvcConfigurerAdapter() {
        	
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                 .allowedMethods(allowedHTTPMethods)
                 .allowedHeaders("*")
                 .allowedOrigins(allowedIP)
                 .allowCredentials(true);
            }
        };
    }
}
