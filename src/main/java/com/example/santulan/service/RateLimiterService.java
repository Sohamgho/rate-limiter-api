package com.example.santulan.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class RateLimiterService {
	private final Map<String, Bucket> bucket = new ConcurrentHashMap<>();
	public Bucket resolveBucket(String apiKey) {
		return bucket.computeIfAbsent(apiKey, this::newBucket);
	}
	public Bucket newBucket(String apiKey) {
		Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
		Bandwidth bw = Bandwidth.classic(10, refill);
		return Bucket.builder().addLimit(bw).build();
	}
}
