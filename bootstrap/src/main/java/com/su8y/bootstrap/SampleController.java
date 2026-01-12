package com.su8y.bootstrap.controller;

import com.su8y.bootstrap.dto.SampleRequest;
import com.su8y.bootstrap.service.SampleService;
import com.su8y.common.logging.time.LogExecutionTime;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
@Slf4j
public class SampleController {

	private final SampleService sampleService;

	@LogExecutionTime
	@GetMapping("/auth-success")
	@PreAuthorize("isAuthenticated()")
	public String getSuccessWithAuth() {
		log.info("SampleController.getSuccess()");
		String result = sampleService.getSuccessData();
		return result;
	}
	@LogExecutionTime
	@GetMapping("/success")
	public String getSuccess() {
		log.info("SampleController.getSuccess()");
		String result = sampleService.getSuccessData();
		return result;
	}

	@LogExecutionTime
	@GetMapping("/error")
	public String getError() {
		sampleService.makeBusinessException();
		return "성공";
	}

	@LogExecutionTime
	@PostMapping("/validation")
	public String postValidation(@Valid @RequestBody SampleRequest request) {
		return "성공";
	}
}
