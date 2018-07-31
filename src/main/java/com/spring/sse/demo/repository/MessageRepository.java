package com.spring.sse.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.spring.sse.demo.model.Message;

/**
 * @author mkarki
 */
public interface MessageRepository extends ReactiveCrudRepository<Message, String> {
}
