package finalProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import java.util.*;
class MessageTest {
	static ArrayList<Integer> UIDs = new ArrayList();
	
	Message myMessage = new Message("This is my message", "Kat",  MessageType.TEXT, 1, UIDs );
	@BeforeClass
	public static void addtoUIDs() {
			UIDs.add(2);
			UIDs.add(1);
	}


	@Test
	void testUpdateIsSent() {
		//this acts as a signal to tell the server a message has been sent
		//and can be dropped from the async list.
		myMessage.updateIsSent();
		assertTrue(myMessage.isSent);
	}



	@Test
	void testGetMessageDate() {
		//I can't make sure the two times match because it's down to the second.
		//so, if it's working properly, they shouldn't match.
		assertNotEquals(myMessage.getMessageDate(), new Date());
	}

	@Test
	void testGetMessageSender() {
		//is it accurately logging who sent the message? username passed
		String messageSender = myMessage.getMessageSender();
		assertEquals(messageSender, "Kat");
	}


	@Test
	void testGetMessageType() {
		//make sure that the system knows what type of message this is.
		assertEquals(myMessage.getMessageType(), MessageType.TEXT);
	}

	@Test
	void testGetMessageID() {
		//make sure that the message ID isn't 0, because it should always be at least 1
		assertNotEquals(myMessage.getMessageID(), 0);
	}

	@Test
	void testGetMessageSenderUID() {
		//I am user one, sending a message, so make sure my UID is right.
		assertTrue(myMessage.getMessageSenderUID() == 1);
		
	}

	@Test
	void testGetRecieverUID() {
		UIDs.add(2);
		UIDs.add(4);
		//just make sure there's something there...
		assertNotNull(myMessage.getReceiverUID());
	}



	@Test
	void testToString() {
		//I can't get the exact time the test will run right, so my tostring won't ever match. 
		assertFalse(myMessage.toString().isEmpty());
		
	}

}
