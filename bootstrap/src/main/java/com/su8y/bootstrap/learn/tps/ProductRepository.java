package com.su8y.bootstrap.learn.tps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Product p where p.id = :id")
	Optional<Product> findByIdWithLock(@Param("id") Long id);

	@Modifying // 데이터 변경을 알림
	@Query("UPDATE Product p SET p.stock = p.stock - :quantity " +
			"WHERE p.id = :productId AND p.stock >= :quantity")
	int decreaseStock(@Param("productId") Long productId, @Param("quantity") int quantity);

	@Modifying // 데이터 변경을 알림
	@Query("UPDATE Product p SET p.stock = p.stock + :quantity WHERE p.id = :productId")
	int increaseStock(Long productId, int quantity);
}
