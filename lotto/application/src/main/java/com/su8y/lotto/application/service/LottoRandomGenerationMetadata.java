package com.su8y.lotto.application.service;

import com.su8y.lotto.application.common.LottoConstant;
import com.su8y.lotto.domain.GenerationMetadata;

public class LottoRandomGenerationMetadata implements GenerationMetadata {
	@Override
	public String getMethod() {
		return LottoConstant.RANDOM.name();
	}
}
