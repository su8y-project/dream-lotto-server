package com.su8y.lotto.domain;

import com.su8y.common.core.domain.AggregateRoot;
import com.su8y.common.core.domain.TypeId;

import java.time.LocalDateTime;

public class LottoTicket extends AggregateRoot<LottoTicket, LottoTicket.Id> {
	private final LottoNumbers numbers;
	private final GenerationMetadata generationMetadata;
	private final LocalDateTime createdAt;

	public LottoTicket(Id id, LottoNumbers numbers, GenerationMetadata generationMetadata, LocalDateTime createdAt) {
		this.id = id;
		this.numbers = numbers;
		this.generationMetadata = generationMetadata;
		this.createdAt = createdAt;
	}

	public LottoNumbers getNumbers() {
		return numbers;
	}

	public GenerationMetadata getGenerationMetadata() {
		return generationMetadata;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public static class Id extends TypeId<Long> {
		public Id(Long value) {
			super(value);
		}
	}
}
