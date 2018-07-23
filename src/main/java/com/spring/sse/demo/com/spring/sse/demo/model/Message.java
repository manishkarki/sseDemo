package com.spring.sse.demo.com.spring.sse.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mkarki
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
	@JsonProperty("from")
	private String from;
	@JsonProperty("message")
	private String message;
}
