package com.su8y.lotto.application.service;

import com.su8y.lotto.domain.LottoGenerator;

public class LottoRandomGenerationCommand implements LottoGenerator.LottoGenerationCommand {
    @Override
    public String getMethod() {
        return "RANDOM";
    }
}
