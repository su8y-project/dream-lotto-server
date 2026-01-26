package com.su8y.bootstrap.learn.tps;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Service
@RequiredArgsConstructor
public class V4RedisStockService {
	private final StringRedisTemplate redisTemplate;
	private final ProductRepository productRepository;
	private final RedissonClient redissonClient;
	private static final String STOCK_KEY_PREFIX = "stock:product:";
	private static final String LOCK_KEY_PREFIX = "lock:product:";

	public Long decrease(Long productId, int quantity) {
		String key = STOCK_KEY_PREFIX + productId;

		String stockStr = redisTemplate.opsForValue().get(key);
		if (stockStr == null) {
			this.loadStockWithLock(productId, key);
		}
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

	private void loadStockWithLock(Long productId, String stockKey) {
		RLock lock = redissonClient.getLock(LOCK_KEY_PREFIX + productId);

		try {
			// 최대 5초간 획득 대기, 락 획득 시 10초간 점유
			if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
				try {
					// [중요] Double-Checked Locking: 락을 얻은 후 다시 확인
					// 먼저 온 사람이 이미 Redis에 값을 채웠을 수 있음
					if (redisTemplate.opsForValue().get(stockKey) == null) {
						int dbStock = productRepository.findById(productId)
								.orElseThrow(() -> new RuntimeException("상품 없음"))
								.getStock();

						// DB 데이터를 Redis에 채움 (이후 다른 사람들은 락을 얻어도 여기서 걸러짐)
						redisTemplate.opsForValue().set(stockKey, String.valueOf(dbStock));
					}
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
