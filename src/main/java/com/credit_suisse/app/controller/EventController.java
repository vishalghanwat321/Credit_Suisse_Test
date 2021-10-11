package com.credit_suisse.app.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.credit_suisse.app.domain.Event;
import com.credit_suisse.app.domain.EventSpecs;
import com.credit_suisse.app.dto.EventDetails;
import com.credit_suisse.app.service.EventService;
import com.credit_suisse.app.util.builder.SpecificationsBuilder;
import com.credit_suisse.app.util.mapper.EventToEventDetailsMapper;
import com.credit_suisse.app.util.process.JsonStreamingJackson;

@RestController
@RequestMapping(path = "/event")
@CrossOrigin("*")
public class EventController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;

	@Autowired
	private JsonStreamingJackson jsonStreamingJackson;

	@Autowired
	private EventToEventDetailsMapper eventToEventDetailsMapper;

	@GetMapping(path = "/list")
	@ResponseBody
	public Iterable<EventDetails> listUsers(@RequestParam(required = false, value = "page") Integer page,
			@RequestParam(required = false, value = "page_size") Integer pageSize,
			@RequestParam(required = false, value = "sort_props[]") String[] sortProperties,
			@RequestParam(required = false, value = "sort_asc", defaultValue = "true") boolean sortAscending,
			@RequestParam(required = false, value = "alert", defaultValue = "true") boolean alert,
			@RequestParam(required = false, value = "id") String id) throws IllegalArgumentException {

		LOGGER.info("AdminController ::  register user execution started");
		try {
			SpecificationsBuilder<Event> specificationsBuilder = new SpecificationsBuilder<>();
			specificationsBuilder.addSpecification(EventSpecs.filterById((Objects.nonNull(id)) ? id : null));
			specificationsBuilder.addSpecification(EventSpecs.filterByAlert((Objects.nonNull(alert)) ? alert : null));

			if (Objects.nonNull(page) && page >= 0 && Objects.nonNull(pageSize) && pageSize > 0) {
				PageRequest pageRequest = PageRequest.of(page, pageSize, sortAscending ? Direction.ASC : Direction.DESC,
						Optional.ofNullable(sortProperties)
								.map(items -> Arrays.stream(items).filter(StringUtils::isNotBlank))
								.orElse(Stream.of("rowid")).toArray(String[]::new));
				Page<Event> events = this.eventService.getAllEvents(specificationsBuilder.build(), pageRequest);
				return events.map(this.eventToEventDetailsMapper::convert);
			}

			Iterable<Event> eventsList = this.eventService.getAllEvents(specificationsBuilder.build());
			Iterable<EventDetails> eventDtoList = StreamSupport.stream(eventsList.spliterator(), false)
					.map(this.eventToEventDetailsMapper::convert).collect(Collectors.toList());
			Collections.sort((List<EventDetails>) eventDtoList);
			return Objects.nonNull(eventDtoList) ? eventDtoList : null;

		} finally {
			LOGGER.info("AdminController ::  register user execution completed");
		}
	}

	@PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload(@RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) throws Exception {
		LOGGER.info("AdminController ::  register user execution started");
		try {
			LOGGER.info("Uploaded File: ");
			LOGGER.info("Name : " + file.getName());
			LOGGER.info("Type : " + file.getContentType());
			LOGGER.info("Name : " + file.getOriginalFilename());
			LOGGER.info("Size : " + file.getSize());
			this.jsonStreamingJackson.process(file.getInputStream());
			return "File Processed...";
		} finally {
			LOGGER.info("AdminController ::  register user execution completed");
		}
	}
}