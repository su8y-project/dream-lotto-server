package com.su8y.lotto.application.port.in.query;

import com.su8y.lotto.application.common.LottoTicketResponse;

import java.util.List;

public interface LottoGetByUserIdUseCase {
	/**
	 * 사용자가 발급한 로또 번호를 조회
	 * @param userId 사용자 아이디
	 * @return Lotto정보
	 */
	List<LottoTicketResponse> getByUserId(Long userId);
}
