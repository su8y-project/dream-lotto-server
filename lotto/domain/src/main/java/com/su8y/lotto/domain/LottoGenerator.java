package com.su8y.lotto.domain;

public interface LottoGenerator {
	/**
	 * Domain Aggregate Generator
	 *
	 * @see LottoTicket
	 */
	LottoTicket generate(LottoGenerationCommand command);

	boolean isSupport(String lottoType);

	interface LottoGenerationCommand {
		String getMethod();
	}
}
