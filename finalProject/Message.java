package finalProject;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	static Integer messageId = 0;
	Date messageCreated = new Date();
	String messageString;
	String messageSender;
	String messageReceiver;
	Integer messageSenderUID;
	Integer messageReceiverUID;
	Boolean isSent;
	
	MessageType messageType;
	public Message(String messageString, String messageSender, String messageReceiver, MessageType messageType, Integer receiverUID) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
		this.messageType = messageType;
		messageReceiverUID = receiverUID;
		isSent = false;
	}
	public Message(String messageString, String messageSender, String messageReceiver, MessageType messageType, Integer senderUID, Integer receiverUID) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
		SetID();
		this.messageType = messageType;
		messageSenderUID = senderUID;
		messageReceiverUID = receiverUID;
		isSent = false;
	}
	
	
	
	private void SetID() {
		messageId++;

	}
    void setMessageDate(Date date) {
    	this.messageCreated = date;
    }
	
	void updateIsSent() {
		isSent = true;}
	
	
	private void updateMessageType(MessageType newType) {
		messageType = newType;
	}
	
	String getMessageString() {
		return messageString;
	}
	
	Date getMessageDate() {
		return messageCreated;
	}
	
	String getMessageSender() {
		return messageSender;
	}
	String getMessageReceiver() {
		return messageReceiver;
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
		return messageReceiverUID;
	}

	Boolean getIsSent(){

		return isSent;}
	
	public String toString() {
		String myStr = (messageId + "," + messageSender + "," + messageReceiver + "," + messageString + "," + messageType + "," + messageCreated + "\n" );
		return myStr;
	}
	
}
