package finalProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

User myUser = new User("Kat", Role.REGULAR, "myPassword", false, false );

	@Test
	void testSetPassword() {
		myUser.setPassword("newPassword");
		assertEquals(myUser.getPassword(), "newPassword");
	}

	@Test
	void testSignOut() {
		myUser.signOut();
		assertNotEquals(myUser.userIsOnline, true);
		assertNotEquals(myUser.userLoginSuccessful, true);
	}

	@Test
	void testSignIn() {
		myUser.signIn();
		assertEquals(myUser.userIsOnline, true);
		assertEquals(myUser.userIsOnline, true);
	}

	@Test
	void testSetRole() {
		myUser.setRole(Role.IT);
		assertEquals(myUser.getRole(), Role.IT);
	}

	@Test
	void testGetLoginStatus() {
		myUser.signIn();
		assertTrue(myUser.getLoginStatus());
	}

	@Test
	void testGetOnlineStatus() {
		myUser.signIn();
		assertTrue(myUser.getOnlineStatus());
	}

	@Test
	void testGetRole() {
		myUser.setRole(Role.IT);
		assertEquals(myUser.getRole(), Role.IT);
	}

	@Test
	void testGetID() {
        
        assertEquals(myUser.getID(), 4);
    }
	@Test
	void testGetPassword() {
		assertEquals(myUser.getPassword(), "myPassword");
	}

	@Test
	void testGetUserName() {
		assertEquals(myUser.getUserName(), "Kat");
	}

	@Test
	void testToString() {
		assertEquals(myUser.toString(), "Kat,5,REGULAR,myPassword,false,false");
	}

}
