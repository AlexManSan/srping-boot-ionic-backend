package com.cursomc.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void envioEmailConfirmacaoPedido(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		// template método já posso usar um método mesmo que não esteja implementado 
		enviaEmail(sm);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		MimeMessage mm;
		try {
			mm = prepareMimeMessageFromPedido(obj);
			// template método já posso usar um método mesmo que não esteja implementado 
			sendHtmlEmail(mm);
		} 
		catch (MessagingException e) {
			// se der erro no html eu chamo o envio sem ser html
			envioEmailConfirmacaoPedido(obj);
		}
	}
	
	@Override
	public void  sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		// template método já posso usar um método mesmo que não esteja implementado 
		enviaEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha : ");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova Senha: " + newPass);
		return sm;
	}

	// poderá ser acessado pelas subclasses por ser protected
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}

	
	/**
	 * Cria os dados do pedido no template e retorna para ser usado na outra classe
	 * @param obj
	 * @return
	 */
	protected String htmlFromTemplatePedido(Pedido obj) {
		// contexto do thymeleaf
		Context context = new Context();
		// enviado o objeto pedido para o template
		context.setVariable("pedido", obj);
		// para processar precisa de uma instancia do TemplateEngine
		// por padrão o thymeleaf já procura dentro da pasta templates
		return templateEngine.process("email/confirmacaoPedido", context);
	}
}
