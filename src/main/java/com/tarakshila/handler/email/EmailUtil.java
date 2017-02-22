package com.tarakshila.handler.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class EmailUtil {
	public static boolean sendMessage(EmailInfoBean emailInfoBean) {
		try {

			Resource resource = new ClassPathResource("mail.properties");
			Properties propsFile = PropertiesLoaderUtils
					.loadProperties(resource);
			final String username = propsFile.getProperty("user");
			final String password = propsFile.getProperty("password");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailInfoBean.getReceiverEmailId()));
			message.setSubject("Testing Subject");
			message.setContent(emailInfoBean.getMessageBody(),
					"text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}

}
