package finalProject;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import finalProject.Message;


public class Client {
	protected User user = new User();
	public boolean isLoggedIn;
	private boolean isConnected;
	String host = "localhost";
	int port = 1234;
	private Socket s;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private int loginAttempt = 0;
	private Log log = new Log();
	public ArrayList<Message> messages = new ArrayList<Message>();
	public volatile String directory = null;
	public volatile String participants = null;
	// client constructor
	public Client(){
		try {
			// start the connection
			s = new Socket(host,port);
			// get the tunnels to communicate with server
            OutputStream outputStream = s.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = s.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);		                   
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	// login function, takes a user and pw then returns a boolean if login is success
	public Boolean login(String username, String pw) throws IOException, ClassNotFoundException {
		System.out.println("login request sending");
		// send a message to server with credentials
		Message Temp = new Message(username + ":" + pw,username,"Server",MessageType.LOGIN);
		objectOutputStream.writeObject(Temp);
		Temp = (Message) objectInputStream.readObject();
		if(Temp.getMessageString().equals("Unsuccessful")) {
			return false;
		}
		// give the client the info for the user that is logged in
		String[] parse = Temp.getMessageString().split(",");
		user.userName = parse[0];
		user.id = Integer.valueOf(parse[1]);
		user.userRole = Role.valueOf(parse[2]);
		user.userPassword = parse[3];
		user.userIsOnline = Boolean.valueOf(parse[4]);
		user.userLoginSuccessful = Boolean.valueOf(parse[5]);
		isLoggedIn = true;
		loginAttempt = 1;
		System.out.println("Login Success!");
		return isLoggedIn;
	}
	
	// send a message to the server to send to designated client
	public void sendMessage(String msg, String sender, String receiver, String senderUID, ArrayList<Integer> receiverUID ) throws IOException {
	    Boolean executed = false;
	    Message temp = new Message(msg, sender, MessageType.TEXT, Integer.parseInt(senderUID), receiverUID);
	    
	    // Create a chatroom ID based on participants
	    ArrayList<Integer> tempParticipants = new ArrayList<>(receiverUID);
	    tempParticipants.add(Integer.valueOf(senderUID));
	    tempParticipants.sort(null);
	    int chatRoomID = getChatroomID(tempParticipants);

	    // Notify the server about the chatroom
	    notifyServerAboutChatroom(chatRoomID, tempParticipants);

	    // Update the client's chatroom list
	    updateClientChatrooms(chatRoomID, temp);

	    // Send the message to the server
	    objectOutputStream.writeObject(temp);
	    objectOutputStream.flush();
			
	}
	
	private int getChatroomID(ArrayList<Integer> participants) {
	    // Use a hash function or any unique method to generate a chatroom ID based on participants
	    // This ensures that the same group of participants always generates the same chatroom ID
	    // For simplicity, you can concatenate participant IDs and take the hash code
	    String concatenatedParticipants = String.join(",", participants.stream().map(String::valueOf).toArray(String[]::new));
	    return concatenatedParticipants.hashCode();
	}
	private void notifyServerAboutChatroom(int chatRoomID, ArrayList<Integer> participants) throws IOException {
	    // Notify the server about the new chatroom
	    // You may need to define a protocol for this communication
	    // For example, send a special message to the server
	    // with messageType = CHATROOM_CREATED and relevant information
	    Message chatroomCreatedMessage = new Message("", "Server", MessageType.CHATROOM_CREATED, chatRoomID, participants);
	    objectOutputStream.writeObject(chatroomCreatedMessage);
	    objectOutputStream.flush();
	}
	private void updateClientChatrooms(int chatRoomID, Message message) {
	    // Update the client's chatroom list
	    // Check if the chatroom already exists
	    for (Chat chat : user.userChatroomArray) {
	        if (chat.chatRoomID == chatRoomID) {
	            chat.msgs.add(message);
	            return;
	        }
	    }
	    // If the chatroom doesn't exist, create a new one
	    Chat newChat = new Chat(new ArrayList<>(message.getReceiverUID()));
	    newChat.msgs.add(message);
	    user.userChatroomArray.add(newChat);
	}
	
	public void handleMessages() throws ClassNotFoundException, IOException {
		
		Message temp = (Message) objectInputStream.readObject();
		if(temp.messageType.equals(MessageType.TEXT)) {
			receiveMessage(temp);
		}
		else if(temp.messageType.equals(MessageType.DIRECTORY)) {
			System.out.println("entering handleDirectory");
			System.out.println(temp.messageString);
			handleDirectory(temp.messageString);
		}
		else if(temp.messageType.equals(MessageType.GET_NAMES)) {
			System.out.println("entering handleDirectory");
			System.out.println(temp.messageString);
			handleParticipants(temp.messageString);
		}
		
		
	}
	// receive messages sent from the server
	public void receiveMessage(Message msg) throws IOException {
		Boolean executed = false;
	    System.out.println("Hello from receive msg");
	    try {
	        if (objectInputStream == null) {
	            System.out.println("ObjectInputStream is null. Check your initialization.");
	            return;
	        }
	        System.out.println("Before readObject");
	        System.out.println(objectInputStream.readObject().getClass());
	        
			ArrayList<Integer> tempParticipants = msg.getReceiverUID();
			tempParticipants.add(Integer.valueOf(msg.getMessageSenderUID()));
			tempParticipants.sort(null);
			
			
			for(int i = 0; i < user.userChatroomArray.size(); i++) {
				if(user.userChatroomArray.get(i).participants.equals(tempParticipants)) {
					user.userChatroomArray.get(i).msgs.add(msg);
					//gets the Chatroom ID for the current Chatroom and sends it to the message
					//so the message can use it as an attribute
					msg.setChatID(user.userChatroomArray.get(i).getID());
					executed = true;
					break;
				}
			}
			if(executed == false) {
				user.userChatroomArray.add(new Chat(tempParticipants));
				user.userChatroomArray.get(user.userChatroomArray.size()-1).msgs.add(msg);
				//gets the Chatroom ID for the current Chatroom and sends it to the message
				//so the message can use it as an attribute
				msg.setChatID(user.userChatroomArray.get(user.userChatroomArray.size()-1).getID());
			}
	        
	        System.out.println("After readObject");
	        System.out.println(msg.toString());
	        System.out.println("after tostring");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public ArrayList<Chat> getAllChatRooms(){
		return user.userChatroomArray;
	}
	
	public ArrayList<Message> getAllMessages(int chatRoomID){
		for(Chat c : user.userChatroomArray) {
			if(c.chatRoomID == chatRoomID) {
				return c.msgs;
			}
		}
			return null;
	}
	
	public void getParticipantsName(int userID, ArrayList<Integer> participants) throws IOException, ClassNotFoundException {
		Message temp = new Message("", "" , MessageType.GET_NAMES, userID, participants);
		objectOutputStream.writeObject(temp);	
	}
	public void handleParticipants(String participants) {
		this.participants = participants;
		System.out.println(participants);
	}

	// return the logs depending on what user decides
	public String viewLog(String s) {
		if(s == "all") {
			return log.getAllLogs();
		}
		else if(s.contains(".")) {
			return log.filterLogsByDate(s);
		}
		else {
			return log.filterLogsBySender(s);
		}
	}	
	
	// request the server to logout the user
	public Boolean logout() throws IOException, ClassNotFoundException {
		System.out.println("logging out..");
		Message Temp = new Message("",user.userName,"Server",MessageType.LOGOUT);
		objectOutputStream.writeObject(Temp);
		objectOutputStream.reset();
		System.out.println("message to server sent");	
		isLoggedIn = false;
		loginAttempt = 0;
		//isConnected = false;
		System.out.println("logout success");
		return isLoggedIn;
	}
	
	// receive the info of users for GUI use
	public void getUserDirectory(Message msg) throws IOException, ClassNotFoundException {
		System.out.println("directory request sending"); 
		objectOutputStream.writeObject(msg);
		objectOutputStream.reset();	
	}
	
	public void handleDirectory(String directory) {
		System.out.println("inside handle directory");
		this.directory = directory;
		System.out.println(directory);
	}



}
