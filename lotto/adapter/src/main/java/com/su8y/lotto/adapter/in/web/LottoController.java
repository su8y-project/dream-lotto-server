package com.su8y.lotto.adapter.in.web;

import com.su8y.common.api.security.AuthenticatedUser;
import com.su8y.common.api.security.CurrentUser;
import com.su8y.common.api.security.CurrentUserId;
import com.su8y.lotto.application.common.LottoTicketResponse;
import com.su8y.lotto.application.port.in.command.LottoGenerationUseCase;
import com.su8y.lotto.application.port.in.query.LottoGetByUserIdUseCase;
import com.su8y.lotto.application.service.LottoRandomGenerationCommand;
import com.su8y.lotto.domain.LottoGenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lottos")
@RequiredArgsConstructor
public class LottoController {
	private final LottoGenerationUseCase lottoGenerationUseCase;
	private final LottoGetByUserIdUseCase lottoGetByUserIdUseCase;

	@GetMapping("/generate/random")
	public LottoTicketResponse getRandomLotto(@CurrentUser AuthenticatedUser user) {
		LottoGenerator.LottoGenerationCommand command = new LottoRandomGenerationCommand(user.userId());
		return lottoGenerationUseCase.generate(command);
	}

	@GetMapping
	public ResponseEntity<List<LottoTicketResponse>> getLotto(@CurrentUserId long userId) {
		return ResponseEntity.ok(this.lottoGetByUserIdUseCase.getByUserId(userId));
	}
}
