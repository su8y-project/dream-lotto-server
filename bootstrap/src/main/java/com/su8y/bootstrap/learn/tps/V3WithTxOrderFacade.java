package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class V3WithTxOrderFacade implements OrderService {
	private final V3WithTxOrderService orderService;
	private final V3WithTxStockService stockService;


	public Long placeOrder(Long productId, int quantity) {
		stockService.decreaseStockOnly(productId, quantity);

		try {
			// 2. 실제 주문 생성 로직 (별도 Tx)
			return orderService.createOrder(productId, quantity);
		} catch (Exception e) {
			// 3. [보상 트랜잭션] 주문 생성 실패 시 깎인 재고를 다시 복구
			stockService.increaseStockOnly(productId, quantity);
			throw e;
		}
	}
}
