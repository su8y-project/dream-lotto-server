package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class V3OrderService implements OrderService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public V3OrderService(ProductRepository productRepository,
						  OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

	/**
	 * Postgresql InnoDB의 원자적 연산 row 락을 통해서 트랜잭션 범위를 줄인다.
	 * @param productId
	 * @param quantity
	 * @return
	 */
    @Transactional
    public Long placeOrder(Long productId, int quantity) {

        // 1. 상품 조회 (트랜잭션 시작)
		int updatedCount = productRepository.decreaseStock(productId, quantity);

		if (updatedCount == 0) {
			throw new IllegalArgumentException("재고가 부족하거나 상품이 존재하지 않습니다.");
		}

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("상품 없음"));

		// 4. 주문 생성 및 저장
		Order order = new Order(product, quantity);
		orderRepository.save(order);

		return order.getId();
    }

}
