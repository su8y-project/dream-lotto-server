package com.su8y.lotto.application.service;

import com.su8y.lotto.application.common.LottoTicketResponse;
import com.su8y.lotto.application.port.in.command.LottoGenerationUseCase;
import com.su8y.lotto.application.port.port.LottoSavePort;
import com.su8y.lotto.domain.LottoGenerator;
import com.su8y.lotto.domain.LottoGeneratorFactory;
import com.su8y.lotto.domain.LottoTicket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LottoGenerationService implements LottoGenerationUseCase {
	private final LottoGeneratorFactory lottoGeneratorFactory;
	private final LottoSavePort lottoSavePort;

	@Override
	public LottoTicketResponse generate(LottoGenerator.LottoGenerationCommand command) {
		LottoTicket lottoTicket = this.lottoGeneratorFactory.getGenerator(command).generate(command);
		LottoTicket save = this.lottoSavePort.save(lottoTicket);
		return LottoTicketResponse.from(save);
	}
}
