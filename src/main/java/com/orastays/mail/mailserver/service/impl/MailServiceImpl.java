package com.orastays.mail.mailserver.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orastays.mail.mailserver.exceptions.FormExceptions;
import com.orastays.mail.mailserver.helper.MessageUtil;
import com.orastays.mail.mailserver.model.MailModel;
import com.orastays.mail.mailserver.service.MailService;
import com.orastays.mail.mailserver.validation.MailValidation;
import com.plivo.api.Plivo;

@Service
@Transactional
public class MailServiceImpl implements MailService {

	private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	@Autowired
	private MailValidation mailValidation;
	
	@Autowired
	private MessageUtil messageUtil;

	@Override
	public void sendMail(MailModel mailModel) throws FormExceptions, MailSendException {
		
		if (logger.isInfoEnabled()) {
			logger.info("sendEmail -- START");
		}
		
		mailValidation.validateMail(mailModel);
		try {
	        Plivo.init(messageUtil.getBundle("auth.id"), messageUtil.getBundle("auth.token"));
	        
		} catch (Exception e) {
			throw new MailSendException("");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("sendEmail -- END");
		}		
	}
}
