package com.xperta.projects.notif.main;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EMail {

	public static boolean send(String from, String to, String cc, String subject, String text) {
//		String to 				= "bcemkucuk@gmail.com";
//		String from 			= "info@xperta.com.tr";
		String host 			= "rd-eldar.guzelhosting.com";
		final String username 	= "info@xperta.com.tr";
		final String password 	= "INxper2019fo@@";


		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipients(Message.RecipientType.CC,  InternetAddress.parse(cc));
			message.setSubject(subject);
			message.setText(text);

			System.out.print("sending message: ");
			Transport.send(message);
			System.out.println("OK");
			
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return false;
	}	
}
