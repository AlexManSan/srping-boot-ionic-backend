package com.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.email.EmailService;
import com.cursomc.email.SmtpEmailService;
import com.cursomc.services.DBService;

/**
 * Classe responsável por carregar as informações do arquivo de persistência application-dev.properties
 * @author Alex
 *
 */
@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	// pegando valor desta anotação do profile 
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		// se não for create ele retorna sem recriar o banco todo de novo
		if(!"create".equals(strategy)) {
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}
	

	/**
	 * Por ser a implementação test neste método eu informo qual Classe será implementada o emailService
	 * @return
	 */
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
