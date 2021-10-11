package com.credit_suisse.app.configs.beans;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@SpringBootTest
public class CustomInitializingBeanTest {

	@InjectMocks
	CustomInitializingBean customInitializingBean;

	@Test
	public void testModelMapper() {
		assertNotNull(customInitializingBean.modelMapper());
	}

}
