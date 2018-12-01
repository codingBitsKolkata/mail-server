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

	private String emailId;
	private String messageBody;
	private String subject;
	private String filePath;
	private String fileName;
}
