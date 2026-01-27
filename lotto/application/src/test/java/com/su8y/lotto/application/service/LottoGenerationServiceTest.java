package com.su8y.lotto.application.service;

import com.su8y.lotto.application.common.LottoTicketResponse;
import com.su8y.lotto.application.port.port.LottoSavePort;
import com.su8y.lotto.domain.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LottoGenerationServiceTest {

	@InjectMocks
	private LottoGenerationService lottoGenerationService;

	@Mock
	private LottoGeneratorFactory lottoGeneratorFactory;

	@Mock
	private LottoSavePort lottoSavePort;

	@Mock
	private LottoGenerator lottoGenerator;

	@Test
	@DisplayName("로또 티켓을 생성하고 저장")
	void generate_shouldGenerateAndSaveLottoTicket() {
		// given
		LottoGenerator.LottoGenerationCommand command = new LottoRandomGenerationCommand(0L);

		GenerationMetadata generationMetadata = new LottoRandomGenerationMetadata();
		LocalDateTime creationTime = LocalDateTime.now();

		LottoTicket mockLottoTicket = new LottoTicket(new LottoTicket.Id(1L), 0L, new LottoNumbers(List.of(1, 2, 3, 4, 5, 6)), generationMetadata, creationTime);

		when(lottoGeneratorFactory.getGenerator(command)).thenReturn(lottoGenerator);
		when(lottoGenerator.generate(command)).thenReturn(mockLottoTicket);
		when(lottoSavePort.save(mockLottoTicket)).thenReturn(mockLottoTicket);

		// when
		LottoTicketResponse response = lottoGenerationService.generate(command);

		// then
		assertThat(response).isNotNull();
		assertThat(response.ticketId()).isEqualTo(mockLottoTicket.getId().getValue());
		assertThat(response.numbers()).isEqualTo(mockLottoTicket.getNumbers().values());
		assertThat(response.createdAt()).isEqualTo(mockLottoTicket.getCreatedAt());

		verify(lottoGeneratorFactory).getGenerator(command);
		verify(lottoGenerator).generate(command);
		verify(lottoSavePort).save(mockLottoTicket);
	}
}
