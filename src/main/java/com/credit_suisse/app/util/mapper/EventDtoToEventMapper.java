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
import com.credit_suisse.app.dto.EventDto;

@Component
public class EventDtoToEventMapper {

	private static final Logger logger = LoggerFactory.getLogger(EventDtoToEventMapper.class);

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	protected void onPostConstruct() {
		this.initializeTypeMaps();
	}

	private TypeMap<EventDto, Event> typeMapDTO;

	private void initializeTypeMaps() {
		logger.info("initializing mapper to map DTO EventDto to DAO Event...");

		this.typeMapDTO = this.modelMapper.createTypeMap(EventDto.class, Event.class)
				.addMapping(EventDto::getId, Event::setId)
				.addMapping(EventDto::getType, Event::setType)
				.addMapping(EventDto::getHost, Event::setHost);
	}

	public Event convert(EventDto dto) {
		if (Objects.isNull(dto))
			return null;
		return this.typeMapDTO.map(dto);
	}
}
