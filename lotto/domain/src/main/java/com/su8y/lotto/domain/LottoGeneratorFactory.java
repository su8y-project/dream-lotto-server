package com.su8y.lotto.domain;

import java.util.List;

public class LottoGeneratorFactory {
	private final List<LottoGenerator> generators;

	public LottoGeneratorFactory(List<LottoGenerator> generators) {
		this.generators = generators;
	}

	public LottoGenerator getGenerator(String name) {
		return this.generators.stream()
				.filter(g -> g.isSupport(name))
				.findFirst()
				.orElseThrow(() -> new LottoGeneratorCannotSupportException(
						String.format("Not Support Lotto Generator \"%s\"", name)
				));
	}

}
