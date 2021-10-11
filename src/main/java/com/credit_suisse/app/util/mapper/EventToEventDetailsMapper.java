package com.credit_suisse.app.util.mapper;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.domain.Event;
import com.credit_suisse.app.dto.EventDetails;

@Component
public class EventToEventDetailsMapper {

	private static final Logger logger = LoggerFactory.getLogger(EventToEventDetailsMapper.class);

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	protected void onPostConstruct() {
		this.initializeTypeMaps();
	}

	private TypeMap<Event, EventDetails> typeMapDTO;

	private void initializeTypeMaps() {
		logger.info("initializing mapper to map DAO Event to DTO EventDetails...");

		this.typeMapDTO = this.modelMapper.createTypeMap(Event.class, EventDetails.class)
				.addMapping(Event::getId, EventDetails::setId)
				.addMapping(Event::getType, EventDetails::setType)
				.addMapping(Event::getHost, EventDetails::setHost)
				.addMapping(Event::getAlert, EventDetails::setAlert)
				.addMapping(Event::getDuration, EventDetails::setDuration);
	}

	public EventDetails convert(Event dao) {
		if (Objects.isNull(dao))
			return null;
		return this.typeMapDTO.map(dao);
	}
}
