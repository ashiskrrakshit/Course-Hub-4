package com.project.SMS;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.qos.logback.core.model.Model;

@SpringBootApplication
public class SmsApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}


