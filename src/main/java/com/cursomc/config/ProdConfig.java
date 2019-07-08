package com.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.services.EmailService;
import com.cursomc.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {

	/**
	 * Por ser a implementação test neste método eu informo qual Classe será implementada o emailService
	 * @return
	 */
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
