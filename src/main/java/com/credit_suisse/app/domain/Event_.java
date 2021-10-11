package com.credit_suisse.app.domain;

import javax.persistence.metamodel.SingularAttribute;

public class Event_ {

	public static volatile SingularAttribute<Event, Boolean> alert;

	public static volatile SingularAttribute<Event, String> id;

	public static final String ALERT = "alert";

	public static final String ID = "id";
}
