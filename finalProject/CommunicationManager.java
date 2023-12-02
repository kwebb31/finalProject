package finalProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import finalProject.Message;
/*
 * 	Program to display and modify a simple DVD collection
 */

public class CommunicationManager implements Runnable {
	public static Client client = new Client();
	 private volatile boolean isRunning = true;
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		CommunicationManager receive = new CommunicationManager();
		Thread thread = new Thread(receive);
		
  	
		try {
			ArrayList<Integer> test = new ArrayList<Integer>();
			test.add(1);
			client.login("tommy", "password1");
			thread.start();
			
			client.sendMessage("Hello", "tommy", "tommy", "1", test);
			//client.receiveMessage();
			//client.sendMessage("Hey", "tommy", "tommy", "1", test);
			//client.receiveMessage();
			System.out.println("after sending message");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		CommunicationUserInterface CommInterface;
//		CommInterface = new CommunicationGUI(client);
//		CommInterface.processCommands();

	}

	@Override
	public synchronized  void run() {
		while(true) {
			if(client.isLoggedIn) {
				System.out.println("before read Object");
				//Message Temp =(Message)objectInputStream.readObject();
				try {
					client.receiveMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("after read msg");
			}

		}
		
	}

}

