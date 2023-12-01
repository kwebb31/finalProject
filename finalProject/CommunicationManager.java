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

public class CommunicationManager implements Runnable {
	public static Client client;
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
//		ObjectOutputStream objectOutputStream;
//		ObjectInputStream objectInputStream;
		CommunicationManager receive = new CommunicationManager();
		Thread thread = new Thread(receive);
		client = new Client();
     	thread.start();
     	
//		ArrayList<Integer> test = new ArrayList<Integer>();
//		test.add(2);
//		client.login("tommy", "password1");
//		client.sendMessage("Hello", "tommy", "tommy", "1", test);
//		client.receiveMessage();

		CommunicationUserInterface CommInterface;
		CommInterface = new CommunicationGUI(client);
		CommInterface.processCommands();

	}

	@Override
	public void run() {
		while(true) {
			//try {
				System.out.println("hi");
				//client.receiveMessage();
		//	} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
		//	}
		}
		
	}

}

