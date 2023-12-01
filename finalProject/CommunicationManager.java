package finalProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
/*
 * 	Program to display and modify a simple DVD collection
 */

public class CommunicationManager {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {

//		ObjectOutputStream objectOutputStream;
//		ObjectInputStream objectInputStream;
		
     	Client client = new Client();
//		ArrayList<Integer> test = new ArrayList<Integer>();
//		test.add(2);
//		client.login("tommy", "password1");
//		client.sendMessage("Hello", "tommy", "tommy", "1", test);
//		client.receiveMessage();

		CommunicationUserInterface CommInterface;
		CommInterface = new CommunicationGUI(client);
		CommInterface.processCommands();

	}

}

