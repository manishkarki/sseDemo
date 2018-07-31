package com.spring.sse.demo.model;

import java.util.Date;

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
public class StockTransaction {
	String user;
	Stock stock;
	Date when;
}
