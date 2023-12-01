package finalProject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ChatTest {
	
	Chat myChat = new Chat(new ArrayList<Integer>(1));
	 


	@Test
	void testGetParticipantsUID() {
			
			assertNotNull(myChat.getParticipantsUID());
	}

	@Test
	void testGetParticipants() {
		assertNotNull(myChat.getParticipants());
	}


	@Test
	void testStringChatroomID() {
		//check to see if the chatroom knows what room it is
		assertEquals(myChat.StringChatroomID(), "3");
	}

	@Test
	void testChatroomID() {
		
		assertEquals(myChat.ChatroomID(), 1);
	}

}
