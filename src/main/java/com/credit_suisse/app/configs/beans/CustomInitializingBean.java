package com.credit_suisse.app.configs.beans;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomInitializingBean implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(CustomInitializingBean.class);

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Bean
	public ModelMapper modelMapper() {
		logger.info("Initializing ModelMapper...");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
}