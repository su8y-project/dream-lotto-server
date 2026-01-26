package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class V3WithTxOrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
	private final V3WithTxStockService stockService;

    public V3WithTxOrderService(ProductRepository productRepository,
								OrderRepository orderRepository, V3WithTxStockService stockService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
		this.stockService = stockService;
	}

	/**
	 * Postgresql InnoDB의 원자적 연산 row 락을 통해서 트랜잭션 범위를 줄인다.
	 * @param productId
	 * @param quantity
	 * @return
	 */
    @Transactional
    public Long createOrder(Long productId, int quantity) {
		Order order = new Order(productId, quantity);
		orderRepository.save(order);

		return order.getId();
    }

}
