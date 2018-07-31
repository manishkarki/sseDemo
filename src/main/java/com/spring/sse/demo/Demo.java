package com.spring.sse.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;

import com.spring.sse.demo.model.SFMessage;

import reactor.core.publisher.Flux;

/**
 * @author mkarki
 */
public class Demo {

	public static void main(String[] args) throws IOException {
		List<SFMessage> arr = new ArrayList<SFMessage>(){{
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
			add(new SFMessage(UUID.randomUUID(), RandomStringUtils.random(10, true, false)));
		}};
		FileWriter writer = new FileWriter("output.txt");
		for(SFMessage str: arr) {
			writer.write("\n{"+String.valueOf(str)+"\n,");
		}
		writer.close();
	}
}
