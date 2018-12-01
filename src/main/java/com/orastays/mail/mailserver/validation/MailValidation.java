package com.orastays.mail.mailserver.validation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orastays.mail.mailserver.exceptions.FormExceptions;
import com.orastays.mail.mailserver.helper.AuthConstant;
import com.orastays.mail.mailserver.helper.MessageUtil;
import com.orastays.mail.mailserver.model.MailModel;

import ch.qos.logback.classic.pattern.Util;

@Component
public class MailValidation extends AuthorizeUserValidation {

	private static final Logger logger = LogManager.getLogger(MailValidation.class);
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private HttpServletRequest request;
	
	public void validateMail(MailModel mailModel) throws FormExceptions {

		if (logger.isDebugEnabled()) {
			logger.debug("validateSMS -- Start");
		}

	//	Util.printLog(mailModel, AuthConstant.INCOMING, "Send Email", request);
		Map<String, Exception> exceptions = new LinkedHashMap<>();
		if(Objects.nonNull(mailModel)) {
			
			// Validate Email Receiver 
			if(StringUtils.isBlank(mailModel.getEmailReceiverId())) {
				exceptions.put(messageUtil.getBundle("sms.message.null.code"), new Exception(messageUtil.getBundle("sms.message.null.message")));
			}
			
			// Validate Email Body
			if(StringUtils.isBlank(mailModel.getEmailBody())) {
				exceptions.put(messageUtil.getBundle("sms.number.null.code"), new Exception(messageUtil.getBundle("sms.number.null.message")));
			}
			// Validate Email Body
			if(StringUtils.isBlank(mailModel.getEmailSubject())) {
				exceptions.put(messageUtil.getBundle("sms.number.null.code"), new Exception(messageUtil.getBundle("sms.number.null.message")));
			}
		}
		
		if (exceptions.size() > 0)
			throw new FormExceptions(exceptions);
		
		if (logger.isDebugEnabled()) {
			logger.debug("validateSMS -- End");
		}
	}

}
