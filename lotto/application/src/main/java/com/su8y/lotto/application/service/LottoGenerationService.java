package com.su8y.lotto.application.service;

import com.su8y.lotto.application.dto.LottoTicketResponse;
import com.su8y.lotto.application.port.in.LottoGenerationUseCase;
import com.su8y.lotto.domain.LottoGenerator;
import com.su8y.lotto.domain.LottoGeneratorFactory;
import com.su8y.lotto.domain.LottoTicket;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LottoGenerationService implements LottoGenerationUseCase {
	private final LottoGeneratorFactory lottoGeneratorFactory;

	@Override
	public LottoTicketResponse generate(LottoGenerator.LottoGenerationCommand command) {
		LottoTicket lottoTicket = lottoGeneratorFactory.getGenerator(command).generate(command);
		return LottoTicketResponse.from(lottoTicket);
	}
}
