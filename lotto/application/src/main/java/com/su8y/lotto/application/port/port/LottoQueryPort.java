package com.su8y.lotto.application.port.port;

import com.su8y.common.core.domain.QueryRepository;
import com.su8y.lotto.domain.LottoTicket;

import java.time.LocalDate;
import java.util.List;

/**
 * Lotto 정보를 조회
 */
public interface LottoQueryPort extends QueryRepository<LottoTicket, LottoTicket.Id> {
	/**
	 * 사용자가 발급한 로또 번호 조회
	 *
	 * @param userId 사용자 아이디
	 * @return 발급한 로또 정보 리스트
	 */
	List<LottoTicket> findByUserId(long userId);

	/**
	 * 해당 날짜에 발급한 로또 번호 전회
	 *
	 * @param start 검색 시작날짜 (00시00분)
	 * @param end   검색 종료 날짜 (11시 59분 59.999초)
	 * @return 발급한 로또 정보 리스트
	 */
	List<LottoTicket> findByDate(LocalDate start, LocalDate end);
}
