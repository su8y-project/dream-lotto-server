package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class V1OrderService implements OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	public V1OrderService(ProductRepository productRepository,
						  OrderRepository orderRepository) {
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}

	@Transactional
	public Long placeOrder(Long productId, int quantity) {

		// 1. 상품 조회 (트랜잭션 시작)
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("상품 없음"));

		// 2. 검증
		validate(product, quantity);
		// 3. 재고 감소
		product.decreaseStock(quantity);

		// 4. 주문 생성
		Order order = new Order(product, quantity);
		orderRepository.save(order);

		// 트랜잭션 끝에서 flush + commit
		return order.getId();
	}

	private void validate(Product product, int quantity) {
		if (quantity < 0 || product.getStock() - quantity < 0) {
			throw new IllegalArgumentException("수량 오류");
		}
	}
}
