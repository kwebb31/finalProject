package finalProject;

import java.util.*;

public class Message {
	
	static Integer messageId = 0;
	Date messageCreated = new Date();
	String messageString;
	String messageSender;
	String messageReciever;
	Boolean isSent = false;
	MessageType messageType;
	public Message(String messageString, String messageSender, String messageReciever, Boolean isSent MessageType messageType) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageReciever = messageReciever;
		SetID();
		this.isSent = isSent;
		this.messageType = messageType;
	}
	
	
	
	private void SetID() {
		messageId++;

	}
	
	
	void updateIsSent() {
		isSent = true;
	}
	
	Date getMessageDate() {
		return messageCreated;
	}
	String getMessageString() {
		return messageString;
	}
	
	
	Boolean getIsSent(){
	
		return isSent;
	}
	MessageType getMessageType() {
		return messageType;
	}
	Integer getMessageID() {
		return messageId;
	}
	
}
