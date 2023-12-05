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
	Integer chatRoomID;
	ArrayList<Integer> messageReceiverUID = new ArrayList<Integer>();
	Boolean isSent;
	MessageType messageType;
	
	public Message(String messageString, String messageSender, String messageReceiver, MessageType messageType) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		this.messageType = messageType;
		isSent = false;
	}
	public Message(String messageString, String messageSender, MessageType messageType, Integer senderUID, ArrayList<Integer> receiverUID) {
		this.messageString = messageString;
		this.messageSender = messageSender;
		SetID();
		chatRoomID = senderUID;
		this.messageType = messageType;
		messageSenderUID = senderUID;
		messageReceiverUID = receiverUID;
		isSent = false;
	}
	
	void setChatID(Integer chatRoomID) {
		this.chatRoomID = chatRoomID;
	}
	public int getChatID() {
		return chatRoomID;
	}
	
	private void SetID() {
		messageId++;

	}
    void setMessageDate(Date date) {
    	
    	this.messageCreated = date;
    }
	
	void updateIsSent() {
		//when the message gets pushed to the user, mark it as sent
		isSent = true;}
	
	
	private void updateMessageType(MessageType newType) {
		//probably shouldn't need to update a MessageType, 
		//but it's available privately in case the Message class needs to
		messageType = newType;
	}
	
	String getMessageString() {
		return messageString;
	}
	
	Date getMessageDate() {
	//gets the date and time a message was sent
		return messageCreated;
	}
	
	String getMessageSender() {
		//gets the username of the sender
		return messageSender;
	}

	MessageType getMessageType() {
		//returns the type of message (login, text, etc)
		return messageType;
	}
	Integer getMessageID() {
		//returns the message ID 
		return messageId;
	}
	
	Integer getMessageSenderUID() {
		return messageSenderUID;
	}
	
	ArrayList<Integer> getReceiverUID() {
		return messageReceiverUID;
	}

	Boolean getIsSent(){

		return isSent;}
	
	public String toString() {
		String myStr = (messageId + "," + messageSender + "," + messageString + "," + messageType + "," + messageCreated + "," );
		myStr = myStr + ("[");
		for(Integer items:messageReceiverUID) {
			myStr = myStr + (items + ";");
		}
		myStr = myStr + ("]");
		return myStr;
	}
	
}
