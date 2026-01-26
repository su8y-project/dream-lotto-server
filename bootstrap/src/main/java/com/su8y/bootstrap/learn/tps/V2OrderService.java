package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class V2OrderService implements OrderService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public V2OrderService(ProductRepository productRepository,
						  OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

	/**
	 * 비관적 락 명시적으로 select를 할때부터 FOR UPDATE 구문을 사용해서 트랜잭션이 끝날때까지 대기를 한다.
	 * @param productId
	 * @param quantity
	 * @return
	 */
    @Transactional
    public Long placeOrder(Long productId, int quantity) {

        // 1. 상품 조회 (트랜잭션 시작)
        Product product = productRepository.findByIdWithLock(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        // 2. 검증
        validate(product, quantity);
        // 3. 재고 감소
        product.decreaseStock(quantity);

        // 4. 주문 생성
        Order order = new Order(productId, quantity);
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
