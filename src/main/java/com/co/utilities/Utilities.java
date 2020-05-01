package com.co.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.log4j.Logger;

public class Utilities {

	final static Logger logger = Logger.getLogger(Utilities.class);
	Properties properties = new Properties();
	Session emailSession;
	Store store;
	Folder emailFolder;
	Properties prop;
	HashMap<String, Integer> dta;

	public Utilities() {
		loadProperties();
	}

	private void loadProperties() {
		try (InputStream input = Utilities.class.getResourceAsStream("/Mail.properties")) {
			prop = new Properties();
			prop.load(input);

			properties.put("mail.smtp.host", prop.getProperty("mail.smtp.host"));
			properties.put("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
			properties.put("mail.smtp.port", prop.getProperty("mail.smtp.port"));
			properties.put("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable"));
			emailSession = Session.getDefaultInstance(properties);

		} catch (IOException ex) {
			logger.error(ex);
		}

	}

	public void getStore() throws MessagingException {
		store = emailSession.getStore(prop.getProperty("mail.type.store"));
		store.connect(prop.getProperty("mail.smtp.host"), prop.getProperty("mail.user"), prop.getProperty("mail.password"));
	}

	public void openFolder() throws MessagingException {
		emailFolder = store.getFolder("INBOX");
		emailFolder.open(Folder.READ_ONLY);
	}

	public void readFolder() throws MessagingException {
		dta = new HashMap<String, Integer>();
		Message[] messages = emailFolder.getMessages();

		for (int i = 0, n = messages.length; i < n; i++) {
			Message message = messages[i];
			String from = message.getFrom()[0].toString();
			logger.info(from);
		}
	}

	public void closeResources() throws MessagingException {
		emailFolder.close(false);
		store.close();
	}
}
