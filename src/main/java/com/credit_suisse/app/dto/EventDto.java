package com.credit_suisse.app.dto;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "id", required = true)
	private String id;

	@JsonProperty(value = "type")
	private String type;

	@JsonProperty(value = "host")
	private String host;

	@JsonProperty(value = "state", required = true)
	private EventState state;

	@JsonProperty(value = "timestamp", required = true)
	private Long timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	// Uniqueness of EventDto
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "{ " + "id=" + this.id + ", state=" + this.state + ", host=" + this.host + ", type=" + this.type
				+ ", timestamp=" + this.timestamp + " }";
	}

}
