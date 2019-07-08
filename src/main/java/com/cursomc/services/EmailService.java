package com.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Pedido;

public interface EmailService {

	/*métodos que deverão ser implementados por outra classe*/
	void envioEmailConfirmacaoPedido(Pedido obj);
	void enviaEmail(SimpleMailMessage msg);
	
	// Email com Html
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
}
