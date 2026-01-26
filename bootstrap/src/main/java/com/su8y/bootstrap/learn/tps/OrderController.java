package com.su8y.bootstrap.learn.tps;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(@Qualifier("v4WithTxOrderFacade") OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@PermitAll
	public ResponseEntity<Void> order(@RequestBody OrderRequest request) {
		orderService.placeOrder(request.productId(), request.quantity());
		return ResponseEntity.ok().build();
	}

	public record OrderRequest(
			Long productId,
			int quantity
	) {
	}
}
