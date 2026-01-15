package com.su8y.lotto.application.port.in;

import com.su8y.common.core.domain.QueryRepository;
import com.su8y.lotto.domain.LottoTicket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Lotto 정보를 조회
 */
public interface LottoQueryUseCase extends QueryRepository<LottoTicket, LottoTicket.Id> {
	List<LottoTicket> findByUserId(long userId);

	List<LottoTicket> findByDate(LocalDate day);

	List<LottoTicket> findByDate(LocalDateTime start, LocalDateTime end);
}
