package com.su8y.lotto.application.port.port;

import com.su8y.lotto.domain.LottoTicket;

public interface LottoSavePort {
	LottoTicket save(LottoTicket lottoTicket);
}
