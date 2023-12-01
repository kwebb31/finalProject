package finalProject;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Server {
	//private static ArrayList<String> UserIDArray = new ArrayList<String>();
	//private static ArrayList<String> pwArray = new ArrayList<String>();
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	private static ArrayList<Message> asyncMessages  = new ArrayList<Message>();
	private static ArrayList<Chat> chats = new ArrayList<Chat>();
	static ArrayList<Socket> clientsockets;
	private static int activeClients = 0;
	private static ObjectOutputStream objectOutputStream;
	private static ObjectInputStream objectInputStream;
	//private static Log = new Log();
	
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
				System.out.println("Looking for connections");
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
	 	private User current = new User();
		// set the input and outputs
		//socket
		private final Socket s;
		// constructor
		public ClientHandler(Socket socket) {
			this.s = socket;
		}
		public void run() {
		System.out.println("hello");
		users = UserLoader.loadUsersFromFile("users.txt");
		
			 try {
				 loadAsyncMessages();
				 while(true) { 
					 Message temp = (Message) objectInputStream.readObject(); 
					 if (temp.getMessageType().equals(MessageType.LOGIN)) {
						 authenticate(temp);
					 }
					 else if(temp.getMessageType().equals(MessageType.LOGOUT)) {
						 logout(temp);
						 break;
					 }
					 else if(temp.getMessageType().equals(MessageType.TEXT)) {
						 sendSynchronousMessage(temp);
					 }
					 else if(temp.getMessageType().equals(MessageType.DIRECTORY)) {
						 getUserDirectory(temp);
					 }
					 
				 }
			 } catch(IOException e) {	 
			 } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//getter to retrieve the OutputStream for the client we want to work with
		public ObjectOutputStream getObjectOutputStream() throws IOException {
			return new ObjectOutputStream(this.s.getOutputStream());
		}
		private void authenticate(Message msg) throws IOException {
			System.out.println("authing");
			Boolean success = false;
			String temp = msg.getMessageString();
			String[] temp2 = temp.split(":");
			 for (User user : users) {
		            if (user.userName.equals(temp2[0]) && user.getPassword().equals(temp2[1])) {
		                user.signIn();
		                Message temp3 = new Message(user.toString(),"Server",user.userName,MessageType.LOGIN);
		                objectOutputStream.writeObject(temp3);
		                current = user;
		                System.out.println("Login Success");
		                sendAsynchronousMessage();
		                success = true;
		                break;
		            }
		        }
			 if(success == false) {
				 Message temp3 = new Message("Unsuccessful","Server","Unknown User",MessageType.LOGIN);
	                objectOutputStream.writeObject(temp3);
			 }
			 
			 
		}
		// Send the message to the designated client and user
		private void sendSynchronousMessage(Message message) throws IOException {
		    boolean recipientFound = false;
		    for (ClientHandler client : clients) {
		    	for(int s : message.messageReceiverUID){
		         if(client.current.id == s) {
		        	 recipientFound = true;
		        	 client.getObjectOutputStream().writeObject(message);
		        	 break;
		         }
		    	}
		    }
		    if(recipientFound == false) {
		    	//write this message to the file and arraylist associated with async messages
		    	offlineMessage(message);
		    }
		    }
		
		// add the message to an arraylist that will be held by server until users are online
		void offlineMessage(Message message) throws IOException{
			asyncMessages.add(message);
			FileOutputStream writer = new FileOutputStream(new File("asyncMessages.csv"));
			ObjectOutputStream w = new ObjectOutputStream(writer);
			w.writeObject(message);
			writer.close();
			w.close();
		}
		
		// load the messages from file to arraylist on server start
		void loadAsyncMessages() throws IOException, ClassNotFoundException {
			try {
				FileInputStream reader = new FileInputStream(new File("asyncMessages.csv"));
				ObjectInputStream r = new ObjectInputStream(reader);
				while(r.readObject() != null) {
					Message line = (Message) r.readObject();
					asyncMessages.add(line);
				}
				reader.close();
				r.close();

//				while(line != null) {
//					String[] getMessageAlone = line.split("\n");
//					for(String message : getMessageAlone) {
//						String[] parse = line.split(",");
//						Message temp = new Message(parse[3], parse[1],parse[2],MessageType.valueOf(parse[4]),Integer.parseInt(parse[6]),parse[7]);
//						temp.setMessageDate(Date.valueOf(parse[5]));
//						asyncMessages.add(temp);
//						
//					}
//					line = reader.nextLine();
//				}
			}catch (EOFException e)
				{
				    // end of stream
				}
		}
		// load the messages from file to arraylist on server start
		/*
		void loadAsyncMessages() throws IOException {
			BufferedReader reader = new BufferedReader(new FileReader("asyncMessages.csv"));
			String line = reader.readLine();
			while(line != null) {
				String[] parse = line.split(",");
				Message temp = new Message(parse[3], parse[1],parse[2],MessageType.valueOf(parse[4]),Integer.parseInt(parse[6],Integer.parseInt(parse[7])));
				temp.setMessageDate(Date.valueOf(parse[5]));
				asyncMessages.add(temp);
			}
		}
		*/
		// check to send messages to users that have logged in.
		void sendAsynchronousMessage() throws IOException {
			for(Message msg : asyncMessages) {
				for(int s : msg.messageReceiverUID) {
					if(s == current.id && current.userIsOnline == true) {
						objectOutputStream.writeObject(msg);
						if(s == current.id) {
							asyncMessages.remove(msg);
						}
					}
				}

			}
			FileOutputStream writer = new FileOutputStream(new File("asyncMessages.csv"));
			ObjectOutputStream w = new ObjectOutputStream(writer); 
			for(Message msg : asyncMessages) {		
				w.writeObject(msg);
			}
			writer.close();
			w.close();
		}
		
		// logout method, receives a message object with MessageType logout and then signs the user out and clears the instance of user in this client
		void logout(Message msg) throws IOException {
			String temp = msg.getMessageString();
			String[] temp2 = temp.split(":");
			 for (User user : users) {
		            if (user.userName.equals(temp2[0]) && user.getPassword().equals(temp2[1])) {
		                user.signOut();
		                Message temp3 = new Message("Success","Server",user.userName,MessageType.LOGOUT);
		                objectOutputStream.writeObject(temp3);
		                current = new User();
		                System.out.println("Logout Success");
		            }
		        }
		}
		
		private void getUserDirectory(Message temp) throws IOException {
			String userName = temp.getMessageSender();
			String userDirectories = "";
			for(int i =0; i < users.size(); i++) {
				if(users.get(i).getUserName().equals(userName)) {
					continue;
				}
				
				userDirectories+= (users.get(i).getUserName() + "\n");
			}
			
			Message userDirectoryListMessage = new Message(userDirectories, "Server", "Client", MessageType.DIRECTORY);
			objectOutputStream.writeObject(userDirectoryListMessage);
		}
		
		void getUserChats() {
			
		}
	}
}



 
		
