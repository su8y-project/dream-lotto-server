package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class V4WithTxOrderFacade implements OrderService {
	private final V3WithTxOrderService orderService; // order 생성 로직은 같은걸 사용
	private final V4RedisStockService stockService;


	public Long placeOrder(Long productId, int quantity) {
		stockService.decrease(productId, quantity);

		try {
			return orderService.createOrder(productId, quantity);
		} catch (Exception e) {
			stockService.increase(productId, quantity);
			throw e;
		}
	}
}
