package com.spring.sse.demo.service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.spring.sse.demo.model.Message;
import com.spring.sse.demo.repository.MessageRepository;

import reactor.core.publisher.Mono;

/**
 * @author mkarki
 */
@Component
public class MessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private Processor processor;
	@Autowired
	private MessageRepository messageRepository;

	private final SseEmitter emitter = new SseEmitter();

	private final ExecutorService executorService = Executors.newFixedThreadPool(3);


	public SseEmitter getMessages(int count) {
		final SseEmitter emitter = new SseEmitter();
//		processor.processMany(count, executorService)
//				.subscribe(
//						value -> onNext(emitter, value),
//						emitter::completeWithError,
//						emitter::complete
//				);
		return emitter;
	}

	public Mono<Message> save(Message m) {
		LOGGER.info("message received is: sender: {} and message being: {}", m.getSender(), m.getMessage());
		return messageRepository.save(m);
	}

	private void onNext(SseEmitter sseEmitter, Message message) {
		try {
			sseEmitter.send(message, MediaType.APPLICATION_JSON);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

