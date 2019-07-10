package com.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.email.EmailService;
import com.cursomc.email.SmtpEmailService;

/**
 * Classe responsável por carregar as informações do arquivo de persistência application-prod.properties
 * @author Alex
 *
 */
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
