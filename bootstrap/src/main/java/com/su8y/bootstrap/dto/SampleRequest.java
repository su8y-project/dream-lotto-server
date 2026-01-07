package com.su8y.bootstrap.dto;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleRequest {

	@NotEmpty(message = "Name cannot be empty")
	private String name;
}
