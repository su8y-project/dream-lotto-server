package com.su8y.bootstrap.learn.tps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class V3WithTxStockService {

    private final ProductRepository productRepository;

    public V3WithTxStockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	@Transactional
	public void decreaseStockOnly(Long productId, int quantity) {
		int updatedCount = productRepository.decreaseStock(productId, quantity);

		if (updatedCount == 0) {
			throw new IllegalArgumentException("재고가 부족하거나 상품이 존재하지 않습니다.");
		}
	}

	@Transactional
	public void increaseStockOnly(Long productId, int quantity) {
		int updatedCount = productRepository.increaseStock(productId, quantity);

		if (updatedCount == 0) {
			throw new IllegalArgumentException("재고가 부족하거나 상품이 존재하지 않습니다.");
		}
	}
}
