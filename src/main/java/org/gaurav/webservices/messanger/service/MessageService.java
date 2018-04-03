package org.gaurav.webservices.messanger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gaurav.webservices.messanger.database.DatabaseClass;
import org.gaurav.webservices.messanger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		messages.put(1l, new Message(1, "Hello World", "Pappu"));
		messages.put(2l, new Message(2, "Hello NoOne", "Pappu"));
	}
	
	public List<Message> getAllMessges(){
		
		return new ArrayList<>(messages.values());
		
/*
		Message message1 = new Message(54, "Hello World", "Pappu") ;
		Message message2 = new Message(54, "Hello NoOne", "Pappu") ;

		List<Message> messages = new ArrayList<Message>();
		messages.add(message1);
		messages.add(message2);

		return messages;
		*/
	}
	
	public List<Message> getAllMessgesForYears(int year){
		
		List<Message> messagesForYear = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		for(Message message : messages.values()) {
			calendar.setTime(message.getCreated());
			if(calendar.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;		
	}
	
	public List<Message> getAllMessgesPaginated(int start, int size){
		
		ArrayList<Message> list  =  new ArrayList<>(messages.values());
		if(start+size> list.size())
			return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	public Message getMessage(long id) {
		return messages.get(id);
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		
		 if(message.getId() == 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
		
	}
	
	public Message removeMessage(long id) {
		
		return messages.remove(id);
		
	}
	

}
