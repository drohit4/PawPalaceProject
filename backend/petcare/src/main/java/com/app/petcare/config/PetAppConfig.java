package com.app.petcare.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.app.petcare.customeresponse.DeleteResponse;

@Component
public class PetAppConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
