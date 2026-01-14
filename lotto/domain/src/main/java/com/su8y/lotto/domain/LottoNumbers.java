package com.su8y.lotto.domain;


import java.util.List;
import java.util.Set;

/**
 * 로또번호 추첨기 서비스에서 각 로또번호에 대응되는 필드(클래스).
 * 로또 번호에 대한 불변성과 비교연산을 하기위해 레코드 클래스로 구성합니다.
 */
public record LottoNumbers(List<Integer> values) {
	public LottoNumbers {
		values = values.stream().sorted().toList();
		Set<Integer> set = Set.copyOf(values);
		if (set.size() != values.size()) {
			throw new LottoNumbersGenerationException("Duplicate numbers are not allowed.");
		}
	}
}
