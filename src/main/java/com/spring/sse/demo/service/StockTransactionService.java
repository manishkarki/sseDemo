package com.spring.sse.demo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.spring.sse.demo.com.spring.sse.demo.model.Stock;
import com.spring.sse.demo.com.spring.sse.demo.model.StockTransaction;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

/**
 * @author mkarki
 */
@Service
public class StockTransactionService implements CommandLineRunner {
	private List<Stock> stockList = new ArrayList<>();
	private List<String> stockNames = Arrays.asList("mango,banana,guava,infinity".split(","));

	public Flux<StockTransaction> getStockTransactions() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		interval.subscribe((i) -> stockList.forEach(stock -> stock.setPrice(changePrice(stock.getPrice()))));

		Flux<StockTransaction> stockTransactionFlux = Flux.fromStream(
				Stream.generate(() -> new StockTransaction(getRandomUser(), getRandomStock(), new Date())));
		return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
	}

	@Override
	public void run(String... args) throws Exception {
		generateRandomStockPrice();
		createRandomStock();
	}

	public void createRandomStock() {
		stockNames.forEach(stockName -> {
			stockList.add(new Stock(stockName, generateRandomStockPrice()));
		});
	}

	public float generateRandomStockPrice () {
		float min = 30;
		float max = 50;
		return min + roundFloat(new Random().nextFloat() * (max - min));
	}

	public float changePrice(float price) {
		return roundFloat(Math.random() >= 0.5 ? price * 1.05f : price * 0.95f);
	}

	public String getRandomUser() {
		String users[] = "Nikhil,Jimmy,Ben,Ian,Manish,Leo".split(",");
		return users[new Random().nextInt(users.length)];
	}

	public Stock getRandomStock() {
		return stockList.get(new Random().nextInt(stockList.size()));
	}

	public float roundFloat(float number) {
		return Math.round(number * 100.0) / 100.0f;
	}
}
