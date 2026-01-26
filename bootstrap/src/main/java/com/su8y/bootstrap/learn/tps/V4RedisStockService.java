package com.su8y.bootstrap.learn.tps;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class V4RedisStockService {
	private final StringRedisTemplate redisTemplate;
	private static final String STOCK_KEY_PREFIX = "stock:product:";

	public Long decrease(Long productId, int quantity) {
		String key = STOCK_KEY_PREFIX + productId;

		Long remain = redisTemplate.opsForValue().decrement(key, quantity);

		if (remain < 0) {
			redisTemplate.opsForValue().increment(key, quantity);
			throw new RuntimeException("재고가 부족합니다. (현재 재고: " + (remain + quantity) + ")");
		}
		return remain;
	}

	public void increase(Long productId, int quantity) {
		String key = STOCK_KEY_PREFIX + productId;
		redisTemplate.opsForValue().increment(key, quantity);
	}
}
