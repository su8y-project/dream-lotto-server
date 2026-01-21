package com.su8y.bootstrap.learn.tps;

import jakarta.persistence.*;

import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;

    protected Order() {}

    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
