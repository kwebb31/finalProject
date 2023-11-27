package finalProject;

import java.util.*;

public class Message {
	
	static Integer messageId = 0;
	Date messageCreated = new Date();
	String messageString;
	String messageSender;
	String messageReciever;
	MessageStatus messageStatus;
	public Message(String messageString, String messageSender, String messageReciever, MessageStatus messageStatus) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageReciever = messageReciever;
		SetID();
		this.messageStatus = messageStatus;
	}
	
	
	
	private void SetID() {
		messageId++;

	}
	
	
	void updateMessageStatus(MessageStatus newStatus) {
		messageStatus = newStatus;
	}
	
	Date getMessageDate() {
		return messageCreated;
	}
	String getMessageString() {
		return messageString;
	}
	
	
	
	MessageStatus getMessageStatus() {
		return messageStatus;
	}
	Integer getMessageID() {
		return messageId;
	}
	
}
