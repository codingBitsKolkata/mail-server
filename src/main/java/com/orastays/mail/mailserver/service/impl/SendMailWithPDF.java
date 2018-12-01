package com.phillippay.common;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phillippay.exception.MailSendException;

@Component
public class SendMailWithPDF {
	
	@Autowired
	private MessageQueryConfig messageUtil;
	
	@Autowired
	private SmtpAuthenticator smtpAuthenticator;

	public boolean send(String emailId, String messageBody, String subject, String filePath) throws MailSendException {
		boolean send = false;
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
			String sysmail= "<br/><br/><br/><br/><br/>This is a system generated mail from Phillip Pay team.Please do not reply to this mail.";
			msgs+= sysmail;
			
			messageBodyPart.setContent(msgs, "text/html");
			
			// Create a related multi-part to combine the parts
			Multipart multipart = new MimeMultipart();
			
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
						
			try {
				message.setFrom(new InternetAddress("adsflaunt@gmail.com", fromEmail));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailId));
			message.setSubject(subject);
			
			if(filePath!=null){
				if(!filePath.equalsIgnoreCase("")){
					DataSource source = new FileDataSource(filePath);
					
					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
						
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName("TransactionDetails.pdf");
					multipart.addBodyPart(messageBodyPart);
		
					// Send the complete message parts
					message.setContent(multipart);
				}
			}

			Transport.send(message);

			System.out.println("Done");
			send = true;
			
		} catch (MessagingException e) {
			throw new MailSendException(e.getMessage());
		}
		
		return send;
	}
	
	public static void main(String[] args) {
		
		SendMail mailer = new SendMail();
	    try {
			mailer.send("avirup.pal1@gmail.com", "test", "test");
		} catch (MailSendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
