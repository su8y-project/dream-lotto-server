package com.su8y.lotto.adapter.adapter.out.persistence;

import com.su8y.lotto.adapter.adapter.out.persistence.entity.LottoTicketEntity;
import com.su8y.lotto.application.port.port.LottoQueryPort;
import com.su8y.lotto.application.port.port.LottoSavePort;
import com.su8y.lotto.domain.LottoTicket;
import com.su8y.lotto.domain.LottoTicket.Id;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LottoRepository implements LottoSavePort, LottoQueryPort {
	private final LottoJpaRepository lottoJpaRepository;

	@Override
	public List<LottoTicket> findByUserId(long userId) {
		return this.lottoJpaRepository.findByUserId(userId).stream()
				.map(LottoTicketEntity::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<LottoTicket> findByDate(LocalDate start, LocalDate end) {
		return this.lottoJpaRepository.findBetweenStartAndEnd(start, end).stream()
				.map(LottoTicketEntity::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<LottoTicket> find(Id id) {
		return this.lottoJpaRepository.findById(id.getValue())
				.map(LottoTicketEntity::toDomain);
	}

	@Override
	public LottoTicket save(LottoTicket lottoTicket) {
		LottoTicketEntity entity = LottoTicketEntity.toEntity(lottoTicket);
		lottoJpaRepository.save(entity);
		return entity.toDomain();
	}

}
