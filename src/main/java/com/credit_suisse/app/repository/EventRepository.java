package com.credit_suisse.app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.credit_suisse.app.domain.Event;

@Repository
public interface EventRepository
		extends PagingAndSortingRepository<Event, Long>, JpaSpecificationExecutor, Serializable {

	boolean existsByIdIgnoreCase(String id);

	Event findByIdIgnoreCase(String id);

}
