package com.spring.sse.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.spring.sse.demo.model.Message;
import com.spring.sse.demo.model.StockTransaction;
import com.spring.sse.demo.service.MessageService;
import com.spring.sse.demo.service.StockTransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author mkarki
 */
@RestController
@RequestMapping("/stock/tx")
public class AppController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

	private final List<SseEmitter> emitters = new ArrayList<>();
	private final StockTransactionService stockTransactionService;
	private final MessageService messageService;

	@Autowired
	public AppController(StockTransactionService stockTransactionService, MessageService messageService) {
		this.stockTransactionService = stockTransactionService;
		this.messageService = messageService;
	}

	@GetMapping(path = "/demo")
	public String getDemoResponse() {
		return "SSE demo";
	}

	/**
	 * @return
	 * @throws IOException
	 */
	@GetMapping(path = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public SseEmitter stream() throws IOException {

		SseEmitter emitter = new SseEmitter();

		emitters.add(emitter);
		emitter.onCompletion(() -> emitters.remove(emitter));

		return emitter;
	}


	@GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<StockTransaction> stockTransactionEvents() {
		return stockTransactionService.getStockTransactions();
	}

	@PostMapping(path = "/chat")
	public Message sendMessage(@Valid @RequestBody Message message) {
		LOGGER.info("message received from:{} and is: {}", message.getSender(), message.getMessage());
		messageService.save(message)
				.flatMap( m -> {
					if(m == null) {
						Mono.error(new RuntimeException());
					}
					return Mono.error(new RuntimeException()).log();
				})
				.onErrorResume(err -> {
					LOGGER.info("saving failed");
					return Mono.error(new Exception("couldn't save the data to db " + err.getStackTrace()));
				}).doOnSuccess(data -> LOGGER.info("saved data successfully"));
		/*emitters.forEach((SseEmitter emitter) -> {
			try {
				messageService.save(message);
				emitter.send(message, APPLICATION_JSON);
			} catch (IOException e) {
				emitter.complete();
				emitters.remove(emitter);
				e.printStackTrace();
			}
		});*/
		return message;
	}

}
