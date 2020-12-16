package com.group5.BVIS.email;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group5.BVIS.LoggerDelegate;

/**
 * Controller to send eMails.
 */
@RestController
public class eMailController {
	
	//autowire Mail Sender
	@Autowired
    public JavaMailSender emailSender;
	
	 @Value("${token}")
	 private String token;
	 
	 private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	/**
	 * Sends an e-mail.
	 * @param token, String. eMail security token.
	 * @param to, String. Target address.
	 * @param subject, String. Subject line of eMail.
	 * @param text, String. Content of eMail
	 * @param response, HttpServletResponse. HTTP status response.
	 */
	@GetMapping("/email/{token}")
	public void sendSimpleMessage(@PathVariable("token") String token, 
			@RequestParam(value="to") String to, 
			@RequestParam(value="subject") String subject, 
			@RequestParam(value="text") String text, 
			HttpServletResponse response) {

		//check token.
		if(token.equals(this.token)) {
		// if no correct password is set, the catch statement is triggered.
			try {
			SimpleMailMessage message = new SimpleMailMessage(); 
			//sending e-mail
	        message.setFrom("bvis.noreply@uni-muenster.de");
				message.setTo(to);
				message.setSubject(subject);
				message.setText(text);
				emailSender.send(message);
			} catch (Exception e) {
				LOGGER.warning("Could not send message to:" + to);
				System.out.println(e.toString());
			}
	        
	        response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	        
	}
}