package com.su8y.lotto.domain;

import java.util.List;

public class LottoGeneratorFactory {
	private final List<LottoGenerator> generators;

	public LottoGeneratorFactory(List<LottoGenerator> generators) {
		this.generators = generators;
	}

	public LottoGenerator getGenerator(LottoGenerator.LottoGenerationCommand command) {
		return this.generators.stream()
				.filter(g -> g.isSupport(command.getMethod()))
				.findFirst()
				.orElseThrow(() -> new LottoGeneratorCannotSupportException(
						String.format("Not Support Lotto Generator \"%s\"", command.getMethod())
				));
	}

}
