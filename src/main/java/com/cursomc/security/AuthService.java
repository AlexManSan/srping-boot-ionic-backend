package com.cursomc.security;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.email.EmailService;
import com.cursomc.exceptions.ObjectNotFoundException;
import com.cursomc.repositories.ClienteRepository;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository cliDao;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente c = cliDao.findByEmail(email);
		if(c == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		String newPass = newPassword();
		c.setSenha(pe.encode(newPass));
		cliDao.save(c);
		emailService.sendNewPasswordEmail(c, newPass);
	}

	/**
	 * Cria um nova senha randomica
	 * @return
	 */
	private String newPassword() {
		char[] vet = new char[10];
		
		for (int i=0; i>10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	/**
	 * Cria senhas em numeros, letras maiúsculas ou minúsculas seguindo a tabela de unicode 
	 * com os números correspondentes ao inícia de cada categoria
	 * @return
	 */
	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { //gera um dígito
			return (char) (rand.nextInt(10)+ 48);
		}
		else if(opt == 1) { //gera letra maiúscula
			return (char) (rand.nextInt(10)+ 65);
		}
		else { // gera letra minúscula
			return (char) (rand.nextInt(10)+ 97);
		}
	}
	

}
