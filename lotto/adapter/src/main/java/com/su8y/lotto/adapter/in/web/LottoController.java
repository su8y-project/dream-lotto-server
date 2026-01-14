package com.su8y.lotto.adapter.in.web;

import com.su8y.lotto.application.dto.LottoTicketResponse;
import com.su8y.lotto.application.port.in.LottoGenerationUseCase;
import com.su8y.lotto.application.service.LottoRandomGenerationCommand;
import com.su8y.lotto.domain.LottoGenerator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lotto")
@RequiredArgsConstructor
public class LottoController {
	private final LottoGenerationUseCase lottoGenerationUseCase;

	@GetMapping("/generate/random")
	public LottoTicketResponse getRandomLotto() {
		LottoGenerator.LottoGenerationCommand command = new LottoRandomGenerationCommand();
		return lottoGenerationUseCase.generate(command);
	}
}
