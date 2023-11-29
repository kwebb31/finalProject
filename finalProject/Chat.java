package finalProject;

import java.util.*;

//note: This might be easier if we just pass an ArrayList with just the UIDs instead of whole user profiles
//since it doesn't matter if they're IT users or 
public class Chat {
	ArrayList<Message>messages = new ArrayList();
	ArrayList<Integer>participants = new ArrayList();
	static Integer chatRoomID = 0;
	
	
	
	
	void chat(ArrayList<Message>newMessages, ArrayList<Integer>newParticipants ) {
		SetID();
		Message myMessage;
		Integer myInt;
		for (int i = 0; i < newMessages.size(); i++) {
			 myMessage = newMessages.get(i);
			 messages.add(myMessage);
		}
		
		for (int i = 0; i < newParticipants.size(); i++) { 
            
            // concatenates the text of existing messages into one long string
           myInt =  newParticipants.get(i); 
           participants.add(myInt);
		}
		}
	
	private void SetID() {
		chatRoomID++;

	}
	void sendMessage(String Sender, ArrayList<Integer>participants) {
		
	}
	Message recieveMessage(Message newMessage) {
		messages.add(newMessage);
	
		return newMessage;

	}

	String getParticipants() {
		String myParticipants = "";
		for (int i = 0; i < participants.size(); i++) 
            
            // concatenate the items in the participants in the arrayList into one string 
           myParticipants = myParticipants.concat(String.valueOf((participants.get(i))) + " "); 
		

		return myParticipants;
	}
	String getMessages() {
		String myMessages = "";
		for (int i = 0; i < messages.size(); i++) 
            
            // concatenates the text of existing messages into one long string
           myMessages = myMessages.concat(String.valueOf((messages.get(i).getMessageString())) + " "); 
		

		return myMessages;
	}
	String StringChatroomID() {
		String myString;
		myString = Integer.toString(chatRoomID);
		return myString;
	}
	Integer ChatroomID() {
		return chatRoomID;
	}
}