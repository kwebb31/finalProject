package finalProject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class LogTest {
	Message myMessage = new Message("Sneakret","Kat", "Tommy", MessageType.TEXT );
	Message aMessage = new Message("Another Sneakret", "Tommy", "Kat", MessageType.TEXT);
	ArrayList<String> myArrayList = new ArrayList();
	Log myLog = new Log();
	User myUser = new User("Kat", Role.IT, "myPassword", true, true );
	
	@BeforeClass
	void myTest()
	{
	myArrayList.add(myMessage.toString());
	myArrayList.add(aMessage.toString());
	}
	@Test
	void testIsIT() {
		assertTrue(myLog.isIT(myUser));
	}

	@Test
	void testGetAllLogs() {
		assertNotNull(myLog.getAllLogs());
	}

	@Test
	void testFilterLogsBySender() {
		assertEquals(myLog.filterLogsBySender("Kat"), "");
	}

	@Test
	void testFilterLogsByDate() {
		String myDate = "5/10/2021";
		
		assertNotNull(myLog.filterLogsByDate(myDate));
	}


}
