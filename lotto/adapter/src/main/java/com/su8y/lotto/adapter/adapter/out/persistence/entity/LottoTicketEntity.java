package com.su8y.lotto.adapter.adapter.out.persistence.entity;

import com.su8y.lotto.application.service.LottoRandomGenerationMetadata;
import com.su8y.lotto.domain.LottoNumbers;
import com.su8y.lotto.domain.LottoTicket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "lotto_ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LottoTicketEntity {
	@Id
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String lottoNumbers; // 1,2,3,4 ...

	@Embedded
	private GenerationMetaDataEntity generationMetaData;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static LottoTicketEntity toEntity(LottoTicket lottoTicket) {
		return LottoTicketEntity.builder()
				.id(lottoTicket.getId().getValue())
				.userId(lottoTicket.getUserId())
				.lottoNumbers(lottoTicket.getNumbers().values().stream()
						.map(String::valueOf)
						.collect(Collectors.joining(",")))
				.generationMetaData(new GenerationMetaDataEntity(lottoTicket.getGenerationMetadata().getMethod()))
				.build();
	}

	public LottoTicket toDomain() {
		List<Integer> lottoNumbers = Arrays.stream(this.getLottoNumbers().split(","))
				.map(Integer::parseInt)
				.toList();

		return new LottoTicket(
				new LottoTicket.Id(this.getId()),
				this.getUserId(),
				new LottoNumbers(lottoNumbers),
				new LottoRandomGenerationMetadata(),
				this.getCreatedAt()
		);
	}
}
