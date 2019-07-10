package com.cursomc.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Classe responsavel por implementar a segurança da aplicação através do spring security e JWT
 * @author Alex
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //vai permitir colocar anotações de perfis nos endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

	// vetor com caminhos liberados
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	// vetor com caminhos liberados somente para leitura get
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**" };
	
	// vetor com caminhos liberados somente para leitura post
	private static final String[] PUBLIC_MATCHERS_POST = { "/clientes/**" };


	// sobrescrever um método do WebSecurityConfigurerAdapter configure
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// verificando se estou no profile test e liberando o h2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		http.cors().and().csrf().disable();
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // permite o vetor somente para o metodo get
			.antMatchers(PUBLIC_MATCHERS).permitAll() // permite o vetor
			.anyRequest().authenticated(); // para todo o restante autenticar
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); // autenticationManager() ja pertence a classe extendida WebSecurityConfigurerAdapter
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * Recebe a implementação de userDetailsService criada por nois com os dados do cliente de acesso mais a classe de criptografia
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	// liberação de cors de multiplas fontes
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	// será usado para criptografar senha 
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
