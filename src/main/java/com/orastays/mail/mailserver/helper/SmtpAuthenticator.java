package com.orastays.mail.mailserver.helper;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmtpAuthenticator extends Authenticator {
	
	@Autowired
	private MessageUtil messageUtil;
	
	public SmtpAuthenticator() {

	    super();
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		
		String username = messageUtil.getBundle("email");//"avirupce@gmail.com";//
		String password = messageUtil.getBundle("email.password");//"7278403639";//
		
	    if ((username != null) && (username.length() > 0) && (password != null) 
	      && (password.length   () > 0)) {

	        return new PasswordAuthentication(username, password);
	    }

	    return null;
	}
}