package com.spring.sse.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.spring.sse.demo.service.SFTransactionService;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SSEDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private SFTransactionService sfTransactionService;


	@Test
	public void contextLoads() {
	}

}
