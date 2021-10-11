package com.credit_suisse.app.util.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.dto.EventDto;
import com.credit_suisse.app.dto.EventState;

@Component
public class JsonStreamingJackson {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonStreamingJackson.class);

	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String HOST = "host";
	public static final String TIME_STAMP = "timestamp";
	public static final String STATE = "state";

	@Autowired
	private EventProcessor eventProcessor;

//	public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
//		new JsonStreamingJackson()
//				.process(JsonStreamingJackson.class.getClassLoader().getResourceAsStream("input_data.json"));
//	}

	public void process(InputStream inputStream) throws Exception {
		if (Objects.nonNull(inputStream)) {
			try {
				// File jsonFile = new File(file);
				JsonFactory jsonfactory = new JsonFactory(); // init factory
				List<EventDto> eventDtoList = null;
				EventDto eventDto = new EventDto();
				JsonParser jsonParser = jsonfactory.createJsonParser(inputStream); // create JSON parser
				JsonToken jsonToken = jsonParser.nextToken();
				while (jsonToken != JsonToken.END_ARRAY) { // Iterate all elements of array
					String fieldname = jsonParser.getCurrentName(); // get current name of token

					if (ID.equalsIgnoreCase(fieldname)) {
						jsonToken = jsonParser.nextToken();
						eventDto.setId(jsonParser.getText());
					}
					if (TYPE.equalsIgnoreCase(fieldname)) {
						jsonToken = jsonParser.nextToken();
						eventDto.setType(jsonParser.getText());
					}
					if (HOST.equalsIgnoreCase(fieldname)) {
						jsonToken = jsonParser.nextToken();
						eventDto.setHost(jsonParser.getText());
					}
					if (TIME_STAMP.equalsIgnoreCase(fieldname)) {
						jsonToken = jsonParser.nextToken();
						eventDto.setTimestamp(jsonParser.getLongValue());
					}
					if (STATE.equalsIgnoreCase(fieldname)) {
						jsonToken = jsonParser.nextToken();
						eventDto.setState(EventState.valueOf(jsonParser.getText()));
					}
					if (jsonToken == JsonToken.END_OBJECT) {
						if (Objects.isNull(eventDtoList)) {
							eventDtoList = new ArrayList<>();
						}
						eventDtoList.add(eventDto);
						eventDto = new EventDto();
					}
					jsonToken = jsonParser.nextToken();
				}
				this.eventProcessor.processData(eventDtoList);
			} catch (IOException e) {
				LOGGER.info("Invalid file...");
				e.printStackTrace();
			}
		} else {
			LOGGER.info("Invalid file / file not present...");
			throw new FileNotFoundException("Invalid file / file not present...");
		}
	}
}
