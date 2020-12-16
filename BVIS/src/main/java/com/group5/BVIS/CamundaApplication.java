package com.group5.BVIS;

import java.util.Properties;
import java.util.logging.Logger;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableProcessApplication("BVIS")
@EnableScheduling
/**
 * Main class to start the application.
 */
public class CamundaApplication {
	public static void main(String... args) {
		SpringApplication.run(CamundaApplication.class, args);

		final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

		LOGGER.info("Process Started \n");		
		
	}
	//get environmental variables
	@Value("${mailUser}")
	private String username;
	
	@Value("${password}")
	private String password;

	/**
	 * service bean, returns object capable of sending e-mails.
	 * @return the JavaMailSender object.
	 */
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("secmail.uni-muenster.de");
		mailSender.setPort(587);

		mailSender.setUsername(this.username);
		mailSender.setPassword(this.password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}