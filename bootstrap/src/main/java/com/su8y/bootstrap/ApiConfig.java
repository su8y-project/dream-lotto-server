package com.su8y.bootstrap;

import com.su8y.lotto.adapter.config.LottoDomainConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LottoDomainConfig.class)
public class ApiConfig {
}
