package com.su8y.lotto.adapter.adapter.out.persistence;

import com.su8y.lotto.adapter.adapter.out.persistence.entity.LottoTicketEntity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LottoJpaRepository extends JpaRepository<LottoTicketEntity, Long> {

	@EntityGraph(attributePaths = {"generationMetaData"})
	List<LottoTicketEntity> findByUserId(long userId);

	@Query("SELECT lt FROM LottoTicketEntity lt WHERE lt.createdAt >= :start AND lt.createdAt < :end")
	List<LottoTicketEntity> findBetweenStartAndEnd(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
