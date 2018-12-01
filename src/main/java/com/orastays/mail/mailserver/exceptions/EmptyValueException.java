package com.orastays.mail.mailserver.exceptions;

public class EmptyValueException extends Exception {

	private static final long serialVersionUID = 5327405044050866283L;
	
	private String name;

	public EmptyValueException(String name) {
		super(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}