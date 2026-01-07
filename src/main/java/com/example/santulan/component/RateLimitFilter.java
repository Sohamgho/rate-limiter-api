package com.example.santulan.component;

import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.santulan.service.RateLimiterService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitFilter extends OncePerRequestFilter{
	 public static final String API_KEY_HEADER = "SIDDHASWARI-111";
	 private final RateLimiterService rateLimiterService;
	 public RateLimitFilter(RateLimiterService rateLimiterService) {
		 this.rateLimiterService = rateLimiterService;
	 }
	 @Override
	 protected void doFilterInternal(HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain)
throws ServletException, IOException {
		 String apiKey = request.getHeader(API_KEY_HEADER);

	        if (apiKey == null || apiKey.isBlank()) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getWriter().write("API Key missing");
	            return;
	        }
	        
	        Bucket bucket = rateLimiterService.resolveBucket(apiKey);
	        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
	        if (probe.isConsumed()) {
	            response.setHeader("X-RateLimit-Remaining",
	                    String.valueOf(probe.getRemainingTokens()));
	            filterChain.doFilter(request, response);
	        } else {
	            response.setStatus(429);
	            response.setHeader("X-RateLimit-Remaining", "0");
	            response.getWriter().write("Too Many Requests");
	        }
	 }
}
