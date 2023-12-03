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
	private String host = "localhost";
	private int port = 1234;
	private Socket s;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private int loginAttempt = 0;
	private Log log = new Log();
	public ArrayList<Message> messages = new ArrayList<Message>();

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
	public synchronized void sendMessage(String msg, String sender, String receiver, String senderUID, ArrayList<Integer> receiverUID ) throws IOException {
			Message Temp = new Message(msg, sender, MessageType.TEXT, Integer.parseInt(senderUID) ,receiverUID);
			objectOutputStream.writeObject(Temp);
			objectOutputStream.flush();
			
	}
	
	// receive messages sent from the server
	public synchronized void receiveMessage() throws IOException {
	    System.out.println("Hello from receive msg");
	    try {
	        if (objectInputStream == null) {
	            System.out.println("ObjectInputStream is null. Check your initialization.");
	            return;
	        }
	        System.out.println("Before readObject");
	        
	        Message temp = (Message) objectInputStream.readObject();
	        System.out.println("After readObject");
	        System.out.println(temp.toString());
	        System.out.println("after tostring");
	        messages.add(temp);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// IN-PROGRESS: collect all of the messages for a chatroom and send to GUI for display
	public ArrayList<Message> displayMessages(String chatroomID){
		ArrayList<Message> display = new ArrayList<Message>();
		for(Message m : messages) {
		
		}
		return display;
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
	public synchronized Boolean logout() throws IOException, ClassNotFoundException {
		System.out.println("logging out..");
		Message Temp = new Message("",user.userName,"Server",MessageType.LOGOUT);
		objectOutputStream.writeObject(Temp);
		System.out.println("message to server sent");	
		isLoggedIn = false;
		loginAttempt = 0;
		//isConnected = false;
		System.out.println("logout success");
		return isLoggedIn;
	}
	
	// receive the info of users for GUI use
	public String getUserDirectory() throws IOException, ClassNotFoundException {
		System.out.println("login request sending");
		Message userDirectory =  new Message(user.getUserName(), user.getUserName(), "Server",MessageType.DIRECTORY);
		objectOutputStream.writeObject(userDirectory);
		userDirectory = (Message) objectInputStream.readObject();
		return userDirectory.getMessageString();
	}
}
