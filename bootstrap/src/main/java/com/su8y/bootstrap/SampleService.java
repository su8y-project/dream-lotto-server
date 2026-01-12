package com.su8y.bootstrap.service;

import com.su8y.common.api.error.BusinessException;
import com.su8y.common.api.error.ErrorCode;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

	public String getSuccessData() {
		return "This is success data from SampleService.";
	}

	public void makeBusinessException() {
		// Assume some business logic fails
		throw new BusinessException(SampleErrorCode.SAMPLE_ERROR); // Or any other custom error
	}

	enum SampleErrorCode implements ErrorCode {
		SAMPLE_ERROR("SAMPLE_01",
				HttpStatus.BAD_REQUEST,
				"validation.failed");
//				"test");

		private final String code;
		private final HttpStatus status;
		private final String messageKey;

		SampleErrorCode(String code, HttpStatus status, String messageKey) {
			this.code = code;
			this.status = status;
			this.messageKey = messageKey;
		}


		@Override
		public String code() {
			return code;
		}

		@Override
		public HttpStatus status() {
			return status;
		}

		@Override
		public String messageKey() {
			return messageKey;
		}
	}
}
