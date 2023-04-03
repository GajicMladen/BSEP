package com.example.SmartHouseAPI.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	private String CERTIFICATE_CREATED_SUBJECT = "Kreacija Sertifikata";

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gofishingteam7@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
		System.out.println("Email sent successfully!");
	}
	
	public void sendCreationEmail(String email,String alias,String privateKey ){
		String confirmationBody = "Poštovani,\n";
		confirmationBody += "Vaš zahtev za potpisivanje sertifikata je uvažen!\n";
		confirmationBody += "Sertifikat možete preuzeti na sledecem linku:\n";
		confirmationBody += "\t http://localhost:8080/api/certificate/"+alias+"\n\n\n";
		confirmationBody += "Psssst! vas privatni kljuc je:\n";
		confirmationBody += privateKey;

		sendSimpleMessage(email, CERTIFICATE_CREATED_SUBJECT, confirmationBody);
	}
}
