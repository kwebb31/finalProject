package finalProject;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ClientTest {
	Client myClient = new Client();
	@Test
	void testClient() {
		assertNotNull(myClient.host);
		assertNotNull(myClient.port);
	}

	@Test
	void testLogin() throws ClassNotFoundException, IOException {
		String myUsername = "kat";
		String myPassword = "password2";
		myClient.login(myUsername, myPassword);
		assertEquals(myClient.isLoggedIn, true);
	}

	@Test
	void testGetAllMessages() {
	//it's okay if it's empty right now, they might not have messages.
	//however, if it's null then it won't add messages properly. 
		assertNotNull(myClient.messages);
	}

	
	@Test
	void testHandleParticipants() {
		assertFalse(myClient.participants.isEmpty());
	}



	@Test
	void testHandleDirectory() {
		//make sure there's something in the directory after the directory is handled
		assertFalse(myClient.directory.isEmpty());
	}

	@Test
	void testLogout() {
		//tests to make sure the log out is correct
		assertFalse(myClient.isLoggedIn);
	}
}
