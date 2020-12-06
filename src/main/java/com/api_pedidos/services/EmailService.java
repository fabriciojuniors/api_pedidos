package com.api_pedidos.services;

import org.springframework.mail.SimpleMailMessage;

import com.api_pedidos.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);

}
