package com.orastays.mail.mailserver.exceptions;

public class MailSendException extends Exception {

	private static final long serialVersionUID = 8122816820501266175L;
	private String name;

	public MailSendException(String name) {
		super(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}