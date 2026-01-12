package com.su8y.lotto.adapter.config;

import com.su8y.lotto.application.port.in.LottoGenerationUseCase;
import com.su8y.lotto.application.service.LottoGenerationService;
import com.su8y.lotto.domain.LottoGenerator;
import com.su8y.lotto.domain.LottoGeneratorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LottoDomainConfig {
	@Bean
	public LottoGeneratorFactory lottoGenerationEngine(List<LottoGenerator> generators) {
		if (generators == null || generators.isEmpty()) {
			throw new IllegalArgumentException("Lotto Generators must not be empty");
		}
		return new LottoGeneratorFactory(generators);
	}
	@Bean
	public LottoGenerationUseCase lottoGenerationUseCase(LottoGeneratorFactory factory) {
		return new LottoGenerationService(factory);
	}
}
