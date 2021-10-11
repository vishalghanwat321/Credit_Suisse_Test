package com.credit_suisse.app.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit_suisse.app.domain.Event;
import com.credit_suisse.app.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	/**
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	@Transactional(readOnly = true)
	public Boolean isEventIdExist(String id) throws IllegalArgumentException {
		Boolean result = false;
		if (Objects.isNull(id))
			throw new IllegalArgumentException("Failed to query Event (reason: invalid event id)...");
		else {
			result = this.repository.existsByIdIgnoreCase(id);
		}
		return result;
	}

	public Event saveEvent(Event event) throws IllegalArgumentException {
		if (Objects.nonNull(event) && !(isEventIdExist(event.getId()))) {
			Event savedEvent = this.repository.save(event);
			return savedEvent;
		} else {
			throw new IllegalArgumentException("Failed to save Event (reason: invalid/duplicate event id)...");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Iterable<Event> getAllEvents(Specification<Event> specification) {
		return this.repository.findAll(specification);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<Event> getAllEvents(Specification<Event> specification, Pageable pageable) {
		return this.repository.findAll(specification, pageable);
	}
}
