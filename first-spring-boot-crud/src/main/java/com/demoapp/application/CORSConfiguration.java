package com.demoapp.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration {
	
	@Bean
	@SuppressWarnings("deprecation")	
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                 .allowedMethods("GET","POST","PUT","DELETE","HEAD")
                 .allowedHeaders("*");
                 //.allowedOrigins("http://localhost:4200","http://192.168.1.20:4200");
            }
        };
    }
}
