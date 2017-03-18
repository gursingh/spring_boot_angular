package com.tarakshila.handler.email;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.tarakshila.service.EmailStatusService;

public class EmailUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailUtil.class);

	public static boolean sendMessage(List<EmailInfoBean> emailInfoBeans,
			EmailStatusService emailStatusService) {
		try {

			Resource resource = new ClassPathResource("mail.properties");
			Properties propsFile = PropertiesLoaderUtils
					.loadProperties(resource);
			final String username = propsFile.getProperty("user");
			final String password = propsFile.getProperty("password");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.transport.protocol", "smtp");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.setProperty("mail.imap.ssl.enable", "true");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});
			Transport t = session.getTransport();
			t.connect();
			try {
				for (EmailInfoBean emailInfoBean : emailInfoBeans) {
					try {
						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(username));
						message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(emailInfoBean
										.getReceiverEmailId()));
						message.setSubject(emailInfoBean.getSubject());
						message.setContent(emailInfoBean.getMessageBody(),
								"text/html; charset=utf-8");
						message.saveChanges();
						t.sendMessage(message, message.getAllRecipients());
						Thread.sleep(1000);
						emailStatusService.updateEmailStatusByApplicantEmail(
								emailInfoBean.getReceiverEmailId(), true);
						logger.info("Done");
					} catch (Exception e) {
						logger.error(e.getMessage());
						continue;
					}

				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				t.close();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return true;
	}
}
