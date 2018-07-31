package com.spring.sse.demo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

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
@Table
public class Message implements Serializable {
	private static final Long serialVersionId = 1L;
	@PrimaryKey
	private String sender;
	private String message;
}
