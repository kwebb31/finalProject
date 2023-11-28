package finalProject;

import java.util.*;

public class Message {
	
	static Integer messageId = 0;
	Date messageCreated = new Date();
	String messageString;
	String messageSender;
	String messageReciever;
	Integer messageSenderUID;
	Integer messageRecieverUID;
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
	
	
	String getMessageSender() {
		return messageSender;
	}
	MessageStatus getMessageStatus() {
		return messageStatus;
	}
	Integer getMessageID() {
		return messageId;
	}
	public String toString() {
		String myStr = (messageId + "," + messageSender + "," + messageReciever + "," + messageString + "," + messageStatus + "," + messageCreated );
		return myStr;
	}
	
}

