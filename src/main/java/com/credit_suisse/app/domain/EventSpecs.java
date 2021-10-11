package com.credit_suisse.app.domain;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

public class EventSpecs {

	public static Specification<Event> filterById(String id) {
		return Optional.ofNullable(id).map(pattern -> {
			String trimmedPattern = pattern.trim();
			if (trimmedPattern.isEmpty())
				return emptyAnd();
			return (Specification<Event>) (root, query, cb) -> cb.like(cb.lower(root.get(Event_.ID)),
					String.format("%%%s%%", trimmedPattern).toLowerCase());
		}).orElse(emptyAnd());
	}

	public static Specification<Event> filterByAlert(Boolean alert) {
		if (alert) {
			return (root, query, cb) -> cb.isTrue(root.get(Event_.ALERT));
		} else {
			return (root, query, cb) -> cb.isFalse(root.get(Event_.ALERT));
		}
	}

	private static Specification<Event> emptyAnd() {
		return (root, query, cb) -> cb.and();
	}
}
