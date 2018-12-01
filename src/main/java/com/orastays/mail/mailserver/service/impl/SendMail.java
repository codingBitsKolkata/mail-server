/**
 * @formatter:off
 *
 */
package com.orastays.common;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.websocket.Session;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableAsync
public class SendMail {
	
	@Autowired
	private MessageQueryConfig messageUtil;
	
	@Autowired
	private SmtpAuthenticator smtpAuthenticator;
	
	@Async
	public void send(String emailId, String messageBody, String subject) throws MailSendException {

		String fromEmail = messageUtil.getBundle("fromEmail");//"mail@example.com";//
		String port = messageUtil.getBundle("port");
		
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		try {

			javax.mail.Message message = new MimeMessage(Session.getDefaultInstance(props, smtpAuthenticator));
			//Message message = new MimeMessage(session);
			BodyPart messageBodyPart = new MimeBodyPart();
			
			String htmlText = "";
			String msgs = htmlText + messageBody;
			String sysmail= "<br/><br/><br/><br/><br/>This is a system generated mail from PhillipPay. Please do not reply to this mail.";
			msgs+= sysmail;
			
			messageBodyPart.setContent(msgs, "text/html");
			
			// Create a related multi-part to combine the parts
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
						
			try {
				message.setFrom(new InternetAddress("no-reply@phillippay.com", fromEmail));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailId));
			message.setSubject(subject);

			Transport.send(message);

			System.out.println("Mail Send");
			
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new MailSendException(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		SendMail mailer = new SendMail();
	    try {
			mailer.send("avirup.pal@gmail.com", "test", "test");
		} catch (MailSendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
