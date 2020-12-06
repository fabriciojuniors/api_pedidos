package com.api_pedidos.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.api_pedidos.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(Pedido pedido);

	void sendHtmlEmail(MimeMessage msg);

}
