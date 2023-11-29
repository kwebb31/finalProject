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
	MessageType messageType;
	public Message(String messageString, String messageSender, String messageReciever, MessageType messageType, Integer senderUID, Integer recieverUID) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageReciever = messageReciever;
		SetID();
		this.messageType = messageType;
		messageSenderUID = senderUID;
		messageRecieverUID = recieverUID;
	}
	
	
	
	private void SetID() {
		messageId++;

	}
	
	
	private void updateMessageType(MessageType newType) {
		messageType = newType;
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
	MessageType getMessageType() {
		return messageType;
	}
	Integer getMessageID() {
		return messageId;
	}
	
	Integer getMessageSenderUID() {
		return messageSenderUID;
	}
	
	Integer getRecieverUID() {
		return messageRecieverUID;
	}
	public String toString() {
		String myStr = (messageId + "," + messageSender + "," + messageReciever + "," + messageString + "," + messageType + "," + messageCreated );
		return myStr;
	}
	
}
