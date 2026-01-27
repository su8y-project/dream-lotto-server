package com.su8y.lotto.application.service;

import com.su8y.lotto.application.common.LottoConstant;
import com.su8y.lotto.domain.LottoGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LottoRandomGenerationCommand implements LottoGenerator.LottoGenerationCommand {
	private final long userId;
	@Override
	public String getMethod() {
		return LottoConstant.RANDOM.name();
	}
	public Long getUserId(){
		return this.userId;
	}
}
