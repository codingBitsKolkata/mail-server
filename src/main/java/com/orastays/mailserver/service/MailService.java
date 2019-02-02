package com.orastays.mailserver.service;


import com.orastays.mailserver.exceptions.MailSendException;
import com.orastays.mailserver.model.MailModel;

public interface MailService {

	void sendMail(MailModel mailModel) throws com.orastays.mailserver.exceptions.FormExceptions, MailSendException;
}