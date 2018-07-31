package com.spring.sse.demo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.sse.demo.model.SFMessage;
import com.spring.sse.demo.model.SFRequest;
import com.spring.sse.demo.model.SFResponse;
import com.spring.sse.demo.repository.SFMessageRepository;

import reactor.core.publisher.Flux;

/**
 * @author mkarki
 */
@Component
public class SFTransactionService {

	private final SFMessageRepository sfMessageRepository;

	@Autowired
	public SFTransactionService(SFMessageRepository sfMessageRepository) {
		this.sfMessageRepository = sfMessageRepository;
	}

	public Flux<SFResponse> saveToDb(SFRequest sfRequest, Long tenantId, Long workspaceId) {
		validateTenantAndWorkspace(tenantId, workspaceId);
		if(sfRequest == null) {
			throw new IllegalArgumentException("A null entity can't be saved");
		}
		List<SFMessage> sfMessages = sfRequest.getSfMessages();
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

		Flux<SFMessage> sfMessageFlux = Flux.fromIterable(sfMessages);

		System.out.println("prints the 10");
		sfMessageFlux.toStream(10).collect(Collectors.toList());

		interval.subscribe((i) -> sfMessageRepository.saveAll(sfMessages));
		Flux<SFResponse> response = Flux.fromStream(
				Stream.generate(() -> new SFResponse(UUID.randomUUID(), "dee"))
		);
		return response;
	}

	//TODO change as per the requirement, currently just a placeholder to validate not already
	// validated headers
	private void validateTenantAndWorkspace(Long tenantId, Long workspaceId) {
		if(tenantId == null || workspaceId == null) {
			throw new IllegalArgumentException("Workspace or tenant cannot be null");
		}
	}

	public Flux<SFMessage> getAlreadySavedEntities() {
		return null;
	}
}
