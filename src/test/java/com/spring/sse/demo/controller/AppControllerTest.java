package com.spring.sse.demo.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.spring.sse.demo.model.Message;

/**
 * @author mkarki
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppControllerTest {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/stock/tx/");

	}

	@Test
	public void testDemoResponse() throws Exception {
		ResponseEntity<String> response = template.getForEntity(createURLWithPort("demo"),
				String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody(), equalTo("SSE demo"));
	}

	@Test
	public void testSendMessage() throws Exception {
		Message message = new Message("manish", "manish is a good boy");
		HttpEntity<Message> httpEntity = new HttpEntity<>(message);
		ResponseEntity<Message> response = template.postForEntity(createURLWithPort("chat"),
				httpEntity, Message.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody().getSender(), is("manish"));
	}

	@Test
	@Ignore
	public void testGetMessage() {
//		byte[] array = new byte[7]; // length is bounded by 7
//		byte[] arrayMessage = new byte[14];
//		new Random().nextBytes(array);
//		String from = new String(array, Charset.forName("UTF-8"));
//		String message = new String(array, Charset.forName("UTF-8"));

		ResponseEntity<SseEmitter> response = template.getForEntity(createURLWithPort("messages"),
				SseEmitter.class);
	}

	private String createURLWithPort(@Nullable String uri) {
		String baseStr = base.toString();
		if (uri != null) { return baseStr + uri; }

		return baseStr;
	}

}
