package com.su8y.bootstrap.learn.tps;

import jakarta.persistence.*;

import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    private Long id;

	@Column(name = "product_id", nullable = false, updatable = false)
	private Long productId;


    private int quantity;

    protected Order() {}

    public Order(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
