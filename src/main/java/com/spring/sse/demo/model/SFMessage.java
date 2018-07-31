package com.spring.sse.demo.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.utils.UUIDs;

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
public class SFMessage implements Serializable {
	private static final Long serialVersionId = 1L;
	@PrimaryKey
	@CassandraType(type = DataType.Name.UUID)
	private UUID id;
	private String value;
}
