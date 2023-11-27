import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Server {
	//static ArrayList<String> UserIDArray;
	//static ArrayList<String> pwArray;
	static ArrayList<User> users;
	static ArrayList<ClientHandler> clients;
	static ArrayList<Socket> clientsockets;
	
	private static int activeClients = 0;
	public static void main(String[] args) {
		ServerSocket ss = null;
		
		try {
			// set server to listen on a port
			ss = new ServerSocket(1234);
			ss.setReuseAddress(true);
			System.out.println("The Group 7 Comms Application Server is Running...");
			
			
			//loop to accept connections
			while (true) {
				Socket client = ss.accept();
				
				System.out.println("New client connected "
						+ client.getInetAddress()
								.getHostAddress());
				// create input and outputs
	            OutputStream outputStream = client.getOutputStream();
	            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
	            InputStream inputStream = client.getInputStream();
	            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream); 
				ClientHandler clientSock = new ClientHandler(client,clients, users);
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
}


//ClientHandler class
 class ClientHandler implements Runnable{
	    // the user using this client
	 	private User currentUser = null;
	 	private final ArrayList<User> users;
		// set the input and outputs
		//socket
		private final Socket s;
		// user is logged in, true or false
		boolean isLoggedIn;
		// constructor
		public ClientHandler(Socket socket,ArrayList<ClientHandler> clients, ArrayList<User> users) {
			this.s = socket;
			this.users = users;
		}
		
		public void run() {
	        // get the input stream from the connected socket
		OutputStream outputStream = null;
	    ObjectOutputStream objectOutputStream = null;
	    InputStream inputStream = null;
	    ObjectInputStream objectInputStream = null;
		 
			 try {
				 outputStream = s.getOutputStream();
	             objectOutputStream = new ObjectOutputStream(outputStream);
	             inputStream = s.getInputStream();
	             objectInputStream = new ObjectInputStream(inputStream);
				 while(true) {
					 Message temp = (Message) objectInputStream.readObject();
				 }
			 } catch(IOException e) {
				 
			 }
		 
       
		}
         
}
		
