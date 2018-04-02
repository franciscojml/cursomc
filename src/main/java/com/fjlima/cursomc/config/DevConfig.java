package com.fjlima.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fjlima.cursomc.services.DBService;
import com.fjlima.cursomc.services.EmailService;
import com.fjlima.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instanciateDatabase() throws ParseException {
		if (!this.strategy.equals("create")) {
			return false;
		}

		dbService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService() throws ParseException {
		return new SmtpEmailService();
	}

}
