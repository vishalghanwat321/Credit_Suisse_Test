package com.credit_suisse.app.util.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.domain.Event;
import com.credit_suisse.app.dto.EventDto;
import com.credit_suisse.app.service.EventService;
import com.credit_suisse.app.util.mapper.EventDtoToEventMapper;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessor.class);

	@Autowired
	private EventService service;

	@Autowired
	private EventDtoToEventMapper eventDtoToEventMapper;

	private Map<String, EventDto> eventDtoMap = new HashMap<>();

	public void processData(List<EventDto> eventDtoList) throws Exception {

		if (Objects.nonNull(eventDtoList)) {
			for (EventDto eventDto : eventDtoList) {
				if (Objects.nonNull(this.eventDtoMap.get(eventDto.getId()))) {
					EventDto eventDtoMapData = this.eventDtoMap.get(eventDto.getId());
					Long duration = Math
							.abs((Objects.nonNull(eventDtoMapData) && Objects.nonNull(eventDtoMapData.getTimestamp())
									? eventDtoMapData.getTimestamp()
									: 0L)
									- (Objects.nonNull(eventDto) && Objects.nonNull(eventDto.getTimestamp())
											? eventDto.getTimestamp()
											: 0L));
					Boolean alert = false;
					if (duration >= 4) {
						alert = true;
					}
					Event event = this.eventDtoToEventMapper.convert(eventDtoMapData);
					event.setAlert(alert);
					event.setDuration(duration);

					try {
						service.saveEvent(event);
					} catch (Exception e) {
						LOGGER.error("Error while saving event details...");
					}

				} else {
					this.eventDtoMap.put(eventDto.getId(), eventDto);
				}
			}
		} else {
			throw new Exception("Invalid list, can't process...");
		}
	}

}