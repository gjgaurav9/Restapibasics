package org.gaurav.webservices.messanger.database;

import java.util.HashMap;
import java.util.Map;

import org.gaurav.webservices.messanger.model.Message;
import org.gaurav.webservices.messanger.model.Profile;

public class DatabaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String , Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
	
	

}
