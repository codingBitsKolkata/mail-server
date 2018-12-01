package com.orastays.mail.mailserver.service;

import org.springframework.mail.MailSendException;

import com.orastays.mail.mailserver.model.MailModel;

public interface MailService {

	void sendMail(MailModel mailModel) throws com.orastays.mail.mailserver.exceptions.FormExceptions, MailSendException;

}
