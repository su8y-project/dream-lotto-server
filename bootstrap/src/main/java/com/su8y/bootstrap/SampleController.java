package com.su8y.bootstrap;

import com.su8y.common.logging.time.LogExecutionTime;
import com.su8y.common.resilience.api.annotation.RateProtection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	@RateProtection(name = "sample")
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
