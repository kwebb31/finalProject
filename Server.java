package finalProject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Server {
	private static ArrayList<String> UserIDArray = new ArrayList<String>();
	private static ArrayList<String> pwArray = new ArrayList<String>();
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	static ArrayList<Socket> clientsockets;
	private static int activeClients = 0;
	private static ObjectOutputStream objectOutputStream;
	private static ObjectInputStream objectInputStream;
	public static void main(String[] args) {
		ServerSocket ss = null;
		
		try {
			// set server to listen on a port
			ss = new ServerSocket(1234);
			ss.setReuseAddress(true);
			System.out.println("The Group 7 Comms Application Server is Running...");
			users = UserLoader.loadUsersFromFile("users.txt");
			
			//loop to accept connections
			while (true) {
				Socket client = ss.accept();
				
				System.out.println("New client connected "
						+ client.getInetAddress()
								.getHostAddress());
				// create input and outputs
	            OutputStream outputStream = client.getOutputStream();
	            objectOutputStream = new ObjectOutputStream(outputStream);
	            InputStream inputStream = client.getInputStream();
	            objectInputStream = new ObjectInputStream(inputStream); 
				ClientHandler clientSock = new ClientHandler(client);
				// create a thread to handle the client
				clients.add(clientSock);
				Thread t = new Thread(clientSock);
				t.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(ss != null) {
				try {
					ss.close();
					}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//ClientHandler class
	private static class ClientHandler implements Runnable{
	    // the user using this client
	 	private User current = null;
	 	private ArrayList<User> users;
		// set the input and outputs
		//socket
		private final Socket s;
		// user is logged in, true or false
		boolean isLoggedIn;
		// constructor
		public ClientHandler(Socket socket) {
			this.s = socket;
			this.users = users;
		}
		
		public void run() {
	        // get the input stream from the connected socket
		//OutputStream outputStream = null;
	    //ObjectOutputStream objectOutputStream = null;
	    //InputStream inputStream = null;
	    //ObjectInputStream objectInputStream = null;
		users = UserLoader.loadUsersFromFile("users.txt"); 
			 try {
				 //OutputStream outputStream = s.getOutputStream();
	             //objectOutputStream = new ObjectOutputStream(outputStream);
	             //InputStream inputStream = s.getInputStream();
	             //objectInputStream = new ObjectInputStream(inputStream);
				 while(true) {
					 Message temp = (Message) objectInputStream.readObject();
					 if (temp.getMessageType().equals(MessageType.LOGIN)) {
						 authenticate(temp);
					 }
				 }
			 } catch(IOException e) {
				 
			 } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
       
		}
		private void authenticate(Message msg) throws IOException {
			String temp = msg.getMessageString();
			String[] temp2 = temp.split(":");
			 for (User user : users) {
		            if (user.userName.equals(temp2[0]) && user.getPassword().equals(temp2[1])) {
		                user.signIn();
		                Message temp3 = new Message(user.toCsvString(),"Server",user.userName,false,MessageType.LOGIN);
		                objectOutputStream.writeObject(temp3);
		                current = user;
		              
		                System.out.println("Login Success");
		            }
		        }
		}
		
		private void sendSynchronousMessage(ArrayList<ClientHandler> clients,Message message) {
		    boolean recipientFound = false;

		    for (ClientHandler client : clients) {
		         if(client.current.userName.equals(message.messageReciever)) {
		        	 
		         }
		            
		            
		            
		        }
		    }
}

}



 
		
