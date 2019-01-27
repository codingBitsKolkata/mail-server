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
import com.orastays.mail.mailserver.helper.Util;
import com.orastays.mail.mailserver.model.MailModel;


@Component
public class MailValidation extends AuthorizeUserValidation {

	private static final Logger logger = LogManager.getLogger(MailValidation.class);
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private HttpServletRequest request;
	
	public void validateMail(MailModel mailModel) throws FormExceptions {

		if (logger.isInfoEnabled()) {
			logger.info("validateMail -- Start");
		}

		Util.printLog(mailModel, AuthConstant.INCOMING, "Send Email", request);
		Map<String, Exception> exceptions = new LinkedHashMap<>();
		if(Objects.nonNull(mailModel)) {
			
			// Validate Email Receiver 
			if(StringUtils.isBlank(mailModel.getEmailId())) {
				exceptions.put(messageUtil.getBundle("emailid.null.code"), new Exception(messageUtil.getBundle("emailid.null.message")));
			}
			
			// Validate Email Body
			if(StringUtils.isBlank(mailModel.getMessageBody())) {
				exceptions.put(messageUtil.getBundle("email.body.null.code"), new Exception(messageUtil.getBundle("email.body.null.message")));
			}
			
			// Validate Email Body
			if(StringUtils.isBlank(mailModel.getSubject())) {
				exceptions.put(messageUtil.getBundle("email.subject.null.code"), new Exception(messageUtil.getBundle("sms.subject.null.message")));
			}
		}
		
		if (exceptions.size() > 0)
			throw new FormExceptions(exceptions);
		
		if (logger.isInfoEnabled()) {
			logger.info("validateMail -- End");
		}
	}
}