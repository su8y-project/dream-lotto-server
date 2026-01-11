package com.su8y.lotto.domain;

import java.util.Set;

public class Lotto {
	Set<RecommendNumber> numbers;

	private static void validate(Set<RecommendNumber> numbers) {
		if (numbers == null || numbers.size() != 6) {
			throw new IllegalArgumentException(
					String.format("Lotto numbers size must be exactly 6. Current size: %d",
							numbers == null ? 0 : numbers.size())
			);
		}
	}
}
