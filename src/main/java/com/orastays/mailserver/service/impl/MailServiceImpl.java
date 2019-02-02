package com.orastays.mailserver.service.impl;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.orastays.mailserver.exceptions.FormExceptions;
import com.orastays.mailserver.exceptions.MailSendException;
import com.orastays.mailserver.helper.MessageUtil;
import com.orastays.mailserver.helper.SmtpAuthenticator;
import com.orastays.mailserver.model.MailModel;
import com.orastays.mailserver.service.MailService;
import com.orastays.mailserver.validation.MailValidation;

@Service
@Configuration
@EnableAsync
public class MailServiceImpl implements MailService {

	private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	@Autowired
	private MailValidation mailValidation;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private SmtpAuthenticator smtpAuthenticator;

	@Override
	public void sendMail(MailModel mailModel) throws FormExceptions, MailSendException {
		
		if (logger.isInfoEnabled()) {
			logger.info("sendEmail -- START");
		}
		
		mailValidation.validateMail(mailModel);
		try {
			if(!StringUtils.isBlank(mailModel.getFilePath()) && !StringUtils.isBlank(mailModel.getFileName())) {
				this.send(mailModel.getEmailId(), mailModel.getMessageBody(), mailModel.getSubject(), mailModel.getFilePath(), mailModel.getFileName());
			} else {
				this.send(mailModel.getEmailId(), mailModel.getMessageBody(), mailModel.getSubject());
			}
	        
		} catch (Exception e) {
			throw new MailSendException("");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("sendEmail -- END");
		}		
	}
	
	@Async
	private void send(String emailId, String messageBody, String subject) throws MailSendException {

		String fromEmail = messageUtil.getBundle("fromEmail");
		String port = messageUtil.getBundle("port");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		try {

			javax.mail.Message message = new MimeMessage(Session.getDefaultInstance(props, smtpAuthenticator));
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "";
			String msgs = htmlText + messageBody;
			String sysmail= "<br/><br/><br/><br/><br/>This is a system generated mail from Orastays. Please do not reply to this mail.";
			msgs+= sysmail;
			messageBodyPart.setContent(msgs, "text/html");
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			message.setFrom(new InternetAddress("no-reply@orastays.com", fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject(subject);
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailSendException(e.getMessage());
		}
	}
	
	@Async
	private void send(String emailId, String messageBody, String subject, String filePath, String fileName) throws MailSendException {
		
		String fromEmail = messageUtil.getBundle("fromEmail");
		String port = messageUtil.getBundle("port");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		try {

			javax.mail.Message message = new MimeMessage(Session.getDefaultInstance(props, smtpAuthenticator));
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "";
			String msgs = htmlText + messageBody;
			String sysmail= "<br/><br/><br/><br/><br/>This is a system generated mail from Orastays.Please do not reply to this mail.";
			msgs+= sysmail;
			messageBodyPart.setContent(msgs, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setFrom(new InternetAddress("no-reply@orastays.com", fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject(subject);
			
			if(filePath != null && !filePath.equalsIgnoreCase("")) {
				DataSource source = new FileDataSource(filePath);
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			}
			Transport.send(message);

		} catch (Exception e) {
			throw new MailSendException(e.getMessage());
		}
	}
}