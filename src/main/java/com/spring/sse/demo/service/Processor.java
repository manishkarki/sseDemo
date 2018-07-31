package com.spring.sse.demo.service;

import org.springframework.stereotype.Component;

import com.spring.sse.demo.model.Message;

/**
 * @author mkarki
 */
@Component
public class Processor extends AbstractProcessor {

	@Override
	public Message processOne() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return new Message("randomPerson", "random person must be nice to send these messages");
	}

}
