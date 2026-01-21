package com.su8y.bootstrap.learn.tps;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "products")
@Getter
public class Product {

    @Id
	@GeneratedValue
    private Long id;

    private String name;

    private int stock;

    protected Product() {}

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("재고 부족");
        }
        this.stock -= quantity;
    }
}
