package com.spring.sse.demo.model;

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
public class Stock {
	String name;
	float price;
}
