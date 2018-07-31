package com.spring.sse.demo.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author mkarki
 */
public abstract class AbstractProcessor<T> {
	public Observable<T> processMany(int count, ExecutorService executorService) {
		final List<Observable<T>> observables = IntStream
				.range(0, count)
				.mapToObj(i -> processOneAsync(executorService))
				.collect(Collectors.toList());
		return Observable.merge(observables);
	}

	private Observable<T> processOneAsync(ExecutorService executorService) {
		return Observable.<T>create(s -> {
			s.onNext(processOne());
			s.onCompleted();
		}).subscribeOn(Schedulers.from(executorService));
	}

	public abstract T processOne();
}
