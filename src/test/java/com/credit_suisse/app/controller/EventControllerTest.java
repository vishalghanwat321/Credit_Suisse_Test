package com.credit_suisse.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.credit_suisse.app.Application;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class EventControllerTest {

	@Autowired
	private MockMvc mockMvc;

	String json_data = "[{\"id\":\"abcdef\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781755},{\"id\":\"def\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781766},{\"id\":\"xyz\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781155},{\"id\":\"abcdef\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781760},{\"id\":\"def\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781764},{\"id\":\"lmn\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781461},{\"id\":\"pqr\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781765},{\"id\":\"xyz\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781159},{\"id\":\"abc\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781455},{\"id\":\"pqr\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781766},{\"id\":\"abc\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"123456\",\"timestamp\":1633781461}]";

	@Test
	public void testEventControllerFileUpload() throws Exception {
		String fileName = "input_data.json";
		MockMultipartFile sampleFile = new MockMultipartFile("file", fileName, "application/json",
				json_data.getBytes());

		MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders
				.multipart("/event/file/upload");
		this.mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testEventControllerForList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/event/list?page={page}&page_size={page_size}", 0, 5))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

}
