package com.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;

	// vetor com caminhos liberados
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	// vetor com caminhos liberados somente para leitura get
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**" };

	// sobrescrever um método do WebSecurityConfigurerAdapter configure
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// verificando se estou no profile test e liberando o h2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // permite o vetor somente para o metodo get
			.antMatchers(PUBLIC_MATCHERS).permitAll() // permite o vetor
			.anyRequest().authenticated(); // para todo o restante autenticar
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	// liberação de cors de multiplas fontes
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
