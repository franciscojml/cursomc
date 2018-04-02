package com.fjlima.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.fjlima.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
