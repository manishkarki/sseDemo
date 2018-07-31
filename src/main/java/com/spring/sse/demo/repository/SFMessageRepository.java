package com.spring.sse.demo.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.spring.sse.demo.model.SFMessage;


/**
 * @author mkarki
 */
@Component
public interface SFMessageRepository extends CrudRepository<SFMessage, UUID> {


}
