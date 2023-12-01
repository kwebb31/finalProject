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
		myMessage.updateIsSent();
		assertTrue(myMessage.isSent);
	}



	@Test
	void testGetMessageDate() {
		assertNotEquals(myMessage.getMessageDate(), new Date());
	}

	@Test
	void testGetMessageSender() {
		String messageSender = myMessage.getMessageSender();
		assertEquals(messageSender, "Kat");
	}


	@Test
	void testGetMessageType() {

		assertEquals(myMessage.getMessageType(), MessageType.TEXT);
	}

	@Test
	void testGetMessageID() {
		assertNotEquals(myMessage.getMessageID(), 0);
	}

	@Test
	void testGetMessageSenderUID() {
		assertTrue(myMessage.getMessageSenderUID() == 1);
		
	}

	@Test
	void testGetRecieverUID() {
		assertNotNull(myMessage.getReceiverUID());
	}



	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
