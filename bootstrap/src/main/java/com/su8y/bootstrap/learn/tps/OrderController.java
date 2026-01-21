package com.su8y.bootstrap.learn.tps;

import com.su8y.common.resilience.api.annotation.CircuitProtection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@CircuitProtection(name = "order")
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
