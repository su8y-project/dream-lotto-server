package com.su8y.lotto.adapter.config;

import com.su8y.common.idgenerator.IdGenerator;
import com.su8y.common.idgenerator.TsidGenerator;
import com.su8y.lotto.application.port.in.LottoGenerationUseCase;
import com.su8y.lotto.application.service.LottoGenerationService;
import com.su8y.lotto.application.service.LottoRandomLottoGenerator;
import com.su8y.lotto.domain.LottoGenerator;
import com.su8y.lotto.domain.LottoGeneratorFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.su8y.lotto")
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

	@Bean
	public LottoGenerator lottoRandomGenerator(IdGenerator idGenerator) {
		return new LottoRandomLottoGenerator(idGenerator);
	}

	@Bean
	@ConditionalOnMissingBean
	public IdGenerator idGenerator(@Value("${tsid.node.id:0}") int nodeId) {
		return new TsidGenerator(nodeId);
	}
}
