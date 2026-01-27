package com.su8y.lotto.application.service;

import com.su8y.lotto.application.common.LottoTicketResponse;
import com.su8y.lotto.application.port.in.query.LottoGetByUserIdUseCase;
import com.su8y.lotto.application.port.port.LottoQueryPort;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LottoQueryService implements LottoGetByUserIdUseCase {
	private final LottoQueryPort lottoQueryPort;

	@Override
	public List<LottoTicketResponse> getByUserId(Long userId) {
		return this.lottoQueryPort.findByUserId(userId).stream()
				.map(LottoTicketResponse::from)
				.toList();
	}
}
