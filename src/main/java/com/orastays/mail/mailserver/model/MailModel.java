package com.orastays.mail.mailserver.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class MailModel {

	private String emailBody;
	private String emailSubject;
	private String emailCC;
	private String emailReceiverId;
}