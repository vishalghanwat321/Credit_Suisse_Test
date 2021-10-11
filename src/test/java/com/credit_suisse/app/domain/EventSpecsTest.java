package com.credit_suisse.app.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventSpecsTest {

	EventSpecs eventSpecs = new EventSpecs();

	@Test
	public void TestfilterByIdForNull() {
		assertNotNull(eventSpecs.filterById(null));
	}

	@Test
	public void TestfilterById() {
		assertNotNull(eventSpecs.filterById("abc"));
	}

	@Test
	public void TestfilterByAlertTrue() {
		assertNotNull(eventSpecs.filterByAlert(true));
	}

	@Test
	public void TestfilterByAlertFalse() {
		assertNotNull(eventSpecs.filterByAlert(false));
	}
}
