package com.su8y.lotto.adapter.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.stereotype.Component;

@Component
public class LottoMessageSourcePostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("errorMessageSource") && bean instanceof AbstractResourceBasedMessageSource) {
			AbstractResourceBasedMessageSource messageSource = (AbstractResourceBasedMessageSource) bean;

			messageSource.addBasenames("classpath:lotto-messages");
		}
		return bean;
	}
}
