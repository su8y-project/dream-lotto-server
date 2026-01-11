package com.su8y.lotto.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 로또번호 추첨기 서비스에서 각 로또번호에 대응되는 필드(클래스).
 * 추천 사유가 다르더라도 number의 기준으로 값을 동일하게 판단해야되기 때문에 record 클래스가 아닌 직접 Object.hashcode를 오버라이딩해서 개발합니다.
 */
@Getter
@RequiredArgsConstructor
public class RecommendNumber implements Serializable {
	private final short number;
	private final Set<String> dependenceWords;
	private final String causeMessage;

	@Override
	public int hashCode() {
		return Objects.hash(this.number);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RecommendNumber that)) return false;
		return this.number == that.number;
	}
}
