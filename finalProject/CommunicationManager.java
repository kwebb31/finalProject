package finalProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import finalProject.Message;

// used to start the GUI and thread that listens for messages
public class CommunicationManager implements Runnable {
	public static Client client = new Client();
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		CommunicationManager receive = new CommunicationManager();
		Thread thread = new Thread(receive);
	
	// COMMENTED OUT CODE IS FOR DEBUGGING
  	/*
		try {
			ArrayList<Integer> test = new ArrayList<Integer>();
			test.add(5);
			client.login("tommy2", "password5");
			//thread.start();
			
			//client.sendMessage("Hello", "tommy", "tommy2", "5", test);
			//client.receiveMessage();
			//client.sendMessage("Hey", "tommy", "tommy", "1", test);
			//client.receiveMessage();
			//System.out.println("after sending message");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		//thread.start();	
		CommunicationUserInterface CommInterface;
		CommInterface = new CommunicationGUI(client);
		CommInterface.processCommands();
	}

	@Override
	// thread action 
	public synchronized void run() {
		while(true) {

			if(client.isLoggedIn) {
				System.out.println("before read Object");
				try {
					client.handleMessages();
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("after read msg");
			}

		}
		
	}

}

