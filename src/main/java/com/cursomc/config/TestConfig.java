package com.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.email.EmailService;
import com.cursomc.email.MockEmailService;
import com.cursomc.services.DBService;

/**
 * Classe responsável por carregar as informações do arquivo de persistência application-test.properties
 * @author Alex
 *
 */
@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;

	/**
	 * Método que faz toda as criações dos itens de banco de dados e seus relacionamentos com os dados
	 * @return
	 * @throws ParseException
	 */
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	/**
	 * Por ser a implementação test neste método eu informo qual Classe será implementada o emailService
	 * @return
	 */
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
