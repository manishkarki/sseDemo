package com.spring.sse.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author mkarki
 */
@Data
@AllArgsConstructor
public class SFRequest {
	@JsonProperty("sfmessages")
	private List<SFMessage> sfMessages;

	public SFRequest() {
		this.sfMessages = new ArrayList<>();
	}

	@JsonIgnore
	public Map<UUID, String> getsfMessagesAsMap() {
		Map<UUID, String> map = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(sfMessages)) {
			for (SFMessage message : sfMessages) {
				map.put(message.getId(), message.getValue());
			}
		}
		return Collections.unmodifiableMap(map);
	}
}
