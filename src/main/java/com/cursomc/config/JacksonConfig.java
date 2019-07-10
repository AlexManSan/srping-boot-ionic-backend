package com.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Esta classe de configuração será executada no carregamento da aplicação
 * 
 * classe responsável por registrar as subclasses de pagamento ao carregar o sistema devido as anotações criadas nas subclasses
 * 
 *  @JsonTypeName("pagamentoComBoleto")
 *	public class PagamentoComBoleto extends Pagamento {
 *
 *	@JsonTypeName("pagamentoComCartao")
 *	public class PagamentoComCartao extends Pagamento {
 *
 * @author Alex
 *
 */
@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}