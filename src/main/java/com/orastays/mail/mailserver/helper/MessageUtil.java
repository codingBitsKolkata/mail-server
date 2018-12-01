package com.orastays.mail.mailserver.helper;

import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

	private Properties msgProperties;

	/**
	 * This method is used to Implement external messageQuery.properties file
	 */
	public MessageUtil() {

		msgProperties = new Properties();
		try {

			msgProperties.load(getClass().getResourceAsStream("/message.properties"));

		} catch (IOException e) {

		}

	}

	public String getBundle(String key) {

		return msgProperties.getProperty(key);
	}

}