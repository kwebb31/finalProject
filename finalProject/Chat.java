package finalProject;

import java.util.*;

//note: This might be easier if we just pass an ArrayList with just the UIDs instead of whole user profiles
//since it doesn't matter if they're IT users or 
public class Chat {
	
	ArrayList<Integer>participants = new ArrayList<Integer>();
	ArrayList<Message>msgs = new ArrayList<Message>();
	int chatRoomID;
	static int chatRoomCounter = 0;
	
	public Chat(ArrayList<Integer> newParticipants) {
		SetID();
		
		//Message myMessage;
		Integer myInt;
		//for (int i = 0; i < newMessages.size(); i++) {
		//	 myMessage = newMessages.get(i);
		//	 messages.add(myMessage);
		//}
		
		for (int i = 0; i < newParticipants.size(); i++) { 
            
            // concatenates the text of existing messages into one long string
           myInt =  newParticipants.get(i); 
           participants.add(myInt);
		}
		}
	

	public void chat( ArrayList<Integer>newParticipants ) {
		SetID();
		//Message myMessage;
		Integer myInt;
		//for (int i = 0; i < newMessages.size(); i++) {
		//	 myMessage = newMessages.get(i);
		//	 messages.add(myMessage);
		//}
		
		for (int i = 0; i < newParticipants.size(); i++) { 
            
            // concatenates the text of existing messages into one long string
           myInt =  newParticipants.get(i); 
           participants.add(myInt);
		}
		}
	
	private void SetID() {
		this.chatRoomID = chatRoomCounter++; 
	}

	ArrayList<Integer> getParticipantsUID(){

		return participants;
	}
	
	String getParticipants() {
		String myParticipants = "";
		for (int i = 0; i < participants.size(); i++) 
            
            // concatenate the items in the participants in the arrayList into one string 
           myParticipants = myParticipants.concat(String.valueOf((participants.get(i))) + " "); 
		

		return myParticipants;
	}
	
	
	//String getMessages() {
	//	String myMessages = "";
		//for (int i = 0; i < messages.size(); i++) 
            
            // concatenates the text of existing messages into one long string
       //    myMessages = myMessages.concat(String.valueOf((messages.get(i).getMessageString())) + " "); 
		

	//	return myMessages;
	//}
	String StringChatroomID() {
		String myString;
		myString = Integer.toString(chatRoomID);
		return myString;
	}
	Integer ChatroomID() {
		return chatRoomID;
	}
}
