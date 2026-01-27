package com.su8y.lotto.application.common;

import com.su8y.lotto.domain.LottoTicket;

import java.time.LocalDateTime;
import java.util.List;

public record LottoTicketResponse(Long ticketId, List<Integer> numbers, LocalDateTime createdAt) {
	public static LottoTicketResponse from(LottoTicket lottoTicket) {
		return new LottoTicketResponse(
				lottoTicket.getId().getValue(),
				lottoTicket.getNumbers().values(),
				lottoTicket.getCreatedAt()
		);
	}
}
