package com.spring.sse.demo.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mkarki
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SFResponse {
	private UUID id;
	private String value;
}
