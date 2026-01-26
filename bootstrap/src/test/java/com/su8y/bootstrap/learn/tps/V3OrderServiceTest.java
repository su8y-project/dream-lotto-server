package com.su8y.bootstrap.learn.tps;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SpringBootTest
class V3OrderServiceTest {

	@Autowired
	private V3OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	private Long productId;

	@BeforeEach
	void setUp() {
		Product product = new Product("테스트상품", 1000);
		productRepository.saveAndFlush(product);
		productId = product.getId();
	}

	@AfterEach
	void tearDown() {
		orderRepository.deleteAll();
		productRepository.deleteAll();
	}

	@Test
	@DisplayName("V3OrderService: 동시에 1,000개 주문 요청 시 재고가 0이 되어야 한다")
	void concurrency_test() throws InterruptedException {
		// given
		int threadCount = 1000; // 총 요청 횟수
		// 멀티스레드 환경을 위해 스레드 풀 생성 (32개 스레드가 동시에 작업)
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					orderService.placeOrder(productId, 1);
				} finally {
					latch.countDown(); // 요청 하나 끝날 때마다 카운트 감소
				}
			});
		}

		latch.await();

		// then
		Product product = productRepository.findById(productId).orElseThrow();

		// 예상: 1,000개 - 1,000번 주문 = 0개
		// 실제(현재 코드): 쓰기 락을 명시적으로 걸어주면서 0개로 맞춰짐.
		System.out.println("최종 남은 재고: " + product.getStock());
		assertThat(product.getStock()).isEqualTo(0);
	}
}
