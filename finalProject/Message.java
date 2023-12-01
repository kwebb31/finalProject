package finalProject;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	static Integer messageId = 0;
	Date messageCreated = new Date();
	String messageString;
	String messageSender;
	
	Integer messageSenderUID;
	ArrayList<Integer> messageReceiverUID = new ArrayList<Integer>();
	Boolean isSent;
	
	MessageType messageType;
	public Message(String messageString, String messageSender, String messageReceiver, MessageType messageType) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageType = messageType;
		isSent = false;
	}
	public Message(String messageString, String messageSender, String messageReceiver, MessageType messageType, Integer senderUID, ArrayList<Integer> receiverUID) {
		this.messageString = messageString;
		this.messageSender = messageSender;
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

	MessageType getMessageType() {
		return messageType;
	}
	Integer getMessageID() {
		return messageId;
	}
	
	Integer getMessageSenderUID() {
		return messageSenderUID;
	}
	
	ArrayList<Integer> getRecieverUID() {
		return messageReceiverUID;
	}

	Boolean getIsSent(){

		return isSent;}
	
	public String toString() {
		String myStr = (messageId + "," + messageSender + "," + messageString + "," + messageType + "," + messageCreated + "\n" );
		return myStr;
	}
	
}
