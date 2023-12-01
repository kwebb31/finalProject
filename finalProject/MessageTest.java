package finalProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;
class MessageTest {
	ArrayList<Integer> UIDs = new ArrayList();
	Message myMessage = new Message("This is my message", "Kat", "Tommy", MessageType.TEXT, 01, UIDs );
	@Test
	void testMessageStringStringStringMessageTypeInteger() {
		fail("Not yet implemented");
	}

	@Test
	void testMessageStringStringStringMessageTypeIntegerInteger() {
		String myString = myMessage.getMessageString();
		String sender = myMessage.getMessageSender();
		MessageType myType = myMessage.getMessageType();
		ArrayList<Integer> messageRecieverUID = myMessage.getRecieverUID();
	}


	@Test
	void testUpdateIsSent() {
		myMessage.updateIsSent();
		assertTrue(myMessage.isSent);
	}



	@Test
	void testGetMessageDate() {
		Date date = new Date();
		Date TestDate = myMessage.getMessageDate();
		assertEquals(date, TestDate);
	}

	@Test
	void testGetMessageSender() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMessageReceiver() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMessageType() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMessageID() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMessageSenderUID() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRecieverUID() {
		fail("Not yet implemented");
	}



	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
