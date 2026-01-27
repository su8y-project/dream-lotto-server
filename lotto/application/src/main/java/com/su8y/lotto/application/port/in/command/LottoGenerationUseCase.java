package com.su8y.lotto.application.port.in.command;

import com.su8y.lotto.application.common.LottoTicketResponse;
import com.su8y.lotto.domain.LottoGenerator;

public interface LottoGenerationUseCase {
	LottoTicketResponse generate(LottoGenerator.LottoGenerationCommand command);

}
