package com.su8y.lotto.application.port.in;

import com.su8y.lotto.application.dto.LottoTicketResponse;
import com.su8y.lotto.domain.LottoGenerator;

public interface LottoGenerationUseCase {
	LottoTicketResponse generate(LottoGenerator.LottoGenerationCommand command);

}
