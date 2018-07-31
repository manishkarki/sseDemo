package com.spring.sse.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sse.demo.model.SFMessage;
import com.spring.sse.demo.model.SFRequest;
import com.spring.sse.demo.service.SFTransactionService;

import reactor.core.publisher.Flux;

/**
 * @author mkarki
 */
@RestController
@RequestMapping("/sf")
public class SFController {

	@Autowired
	private SFTransactionService sfTransactionService;

	@PostMapping
	public SFMessage addToDB(@RequestHeader("tenant-id") Long tenantId,
		@RequestHeader(value = "workspace-id") Long workspaceId, @Valid @RequestBody SFRequest sfRequest) {
		sfTransactionService.saveToDb(sfRequest, tenantId, workspaceId);
		return new SFMessage();
	}

	@GetMapping(value = "/stream/dbEvents", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<SFMessage> getMessages() {
		return sfTransactionService.getAlreadySavedEntities();
	}

}
