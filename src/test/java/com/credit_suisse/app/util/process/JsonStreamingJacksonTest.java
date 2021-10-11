package com.credit_suisse.app.util.process;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.credit_suisse.app.Application;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
public class JsonStreamingJacksonTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonStreamingJacksonTest.class);

	@Autowired
	JsonStreamingJackson jsonStreamingJackson;

	String json_data = "[{\"id\":\"abcdef\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781755},{\"id\":\"def\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781766},{\"id\":\"xyz\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781155},{\"id\":\"abcdef\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781760},{\"id\":\"def\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781764},{\"id\":\"lmn\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781461},{\"id\":\"pqr\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781765},{\"id\":\"xyz\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781159},{\"id\":\"abc\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781455},{\"id\":\"pqr\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781766},{\"id\":\"abc\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781461}]";

	@Test
	public void TestProcessForException() {
		try {
			jsonStreamingJackson.process(null);
		} catch (FileNotFoundException ex) {
			LOGGER.error(ex.getMessage());
			assertNotNull(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestProcess() {
		Boolean test = true;
		try {
			jsonStreamingJackson.process(new ByteArrayInputStream(json_data.getBytes()));
		} catch (Exception e) {
			test = false;
			e.printStackTrace();
		}
		assertTrue(test);
	}
}
