package com.su8y.lotto.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.su8y.common.idgenerator.IdGenerator;
import com.su8y.lotto.application.service.LottoRandomGenerationCommand;
import com.su8y.lotto.application.service.LottoRandomLottoGenerator;
import com.su8y.lotto.domain.LottoTicket;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class LottoRandomLottoGeneratorTest {

	@Mock
	private IdGenerator idGenerator;

	@InjectMocks
	private LottoRandomLottoGenerator lottoRandomLottoGenerator;

	@Test
	@DisplayName("랜덤 로또 티켓을 생성한다")
	void generate_shouldCreateRandomLottoTicket() {
		// given
		Long expectedUuid = 1L;
		when(idGenerator.generate()).thenReturn(expectedUuid);

		LottoRandomGenerationCommand command = new LottoRandomGenerationCommand();

		// when
		LottoTicket lottoTicket = lottoRandomLottoGenerator.generate(command);

		// then
		assertThat(lottoTicket).isNotNull();
		assertThat(lottoTicket.getId()).isNotNull();
		assertThat(lottoTicket.getId().getValue()).isEqualTo(expectedUuid); // ID 값 검증
		assertThat(lottoTicket.getCreatedAt()).isNotNull();

		// 번호 검증
		List<Integer> numbers = lottoTicket.getNumbers().values();
		assertThat(numbers).hasSize(6);
		assertThat(new HashSet<>(numbers)).hasSize(6); // 중복 없는지 확인
		numbers.forEach(number -> assertThat(number).isBetween(1, 45));

		// 메타데이터 검증
		assertThat(lottoTicket.getGenerationMetadata()).isNotNull();
		assertThat(lottoTicket.getGenerationMetadata().getMethod()).isEqualTo("RANDOM");
	}

	@Test
	@DisplayName("isSupport는 'RANDOM' 문자열을 지원한다 (대소문자 무시)")
	void isSupport_shouldSupportRandomString() {
		assertThat(lottoRandomLottoGenerator.isSupport("RANDOM")).isTrue();
		assertThat(lottoRandomLottoGenerator.isSupport("random")).isTrue();
		assertThat(lottoRandomLottoGenerator.isSupport("Random")).isTrue();
	}

	@Test
	@DisplayName("isSupport는 'RANDOM'이 아닌 문자열은 지원하지 않는다")
	void isSupport_shouldNotSupportOtherStrings() {
		assertThat(lottoRandomLottoGenerator.isSupport("MANUAL")).isFalse();
		assertThat(lottoRandomLottoGenerator.isSupport(null)).isFalse();
		assertThat(lottoRandomLottoGenerator.isSupport("")).isFalse();
	}
}
