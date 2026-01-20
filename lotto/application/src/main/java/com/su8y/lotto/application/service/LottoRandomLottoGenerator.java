package com.su8y.lotto.application.service;

import com.su8y.common.idgenerator.IdGenerator;
import com.su8y.lotto.domain.LottoGenerator;
import com.su8y.lotto.domain.LottoNumbers;
import com.su8y.lotto.domain.LottoTicket;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LottoRandomLottoGenerator implements LottoGenerator {
	private static final int LOTTO_NUMBER_COUNT = 6;
	private static final int MIN_LOTTO_NUMBER = 1;
	private static final int MAX_LOTTO_NUMBER = 45;
	private final IdGenerator idGenerator;

	@Override
	public LottoTicket generate(LottoGenerationCommand command) {
		if (command instanceof LottoRandomGenerationCommand userCommand) {
			List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER).boxed().toList());
			Collections.shuffle(numbers);
			List<Integer> lottoNumbers = numbers.subList(0, LOTTO_NUMBER_COUNT);

			return new LottoTicket(
					new LottoTicket.Id(idGenerator.generate()),
					userCommand.getUserId(),
					new LottoNumbers(lottoNumbers),
					new LottoRandomGenerationMetadata(),
					LocalDateTime.now()
			);
		} else {
			throw new IllegalArgumentException("Invalid command type");
		}
	}

	@Override
	public boolean isSupport(String lottoType) {
		return "RANDOM".equalsIgnoreCase(lottoType);
	}

}
