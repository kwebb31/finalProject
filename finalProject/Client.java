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

	public Client(){
		try {
			s = new Socket(host,port);
            OutputStream outputStream = s.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = s.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);		                   
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public Boolean login(String username, String pw) throws IOException, ClassNotFoundException {
		System.out.println("login request sending");
		Message Temp = new Message(username + ":" + pw,username,"Server",MessageType.LOGIN);
		objectOutputStream.writeObject(Temp);
		Temp = (Message) objectInputStream.readObject();
		if(Temp.getMessageString().equals("Unsuccessful")) {
			return false;
		}
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
	public synchronized void sendMessage(String msg, String sender, String receiver, String senderUID, ArrayList<Integer> receiverUID ) throws IOException {
			Message Temp = new Message(msg, sender, MessageType.TEXT, Integer.parseInt(senderUID) ,receiverUID);
			objectOutputStream.writeObject(Temp);
			objectOutputStream.flush();
			
	}
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
	    } catch (Exception e) {
	        // Print the stack trace for debugging
	        e.printStackTrace();
	        // Reset the stream to handle any cached objects
	        //objectInputStream.reset();
	    }
	}

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


	
	public synchronized Boolean logout() throws IOException, ClassNotFoundException {
		System.out.println("logging out..");
		Message Temp = new Message("",user.userName,"Server",MessageType.LOGOUT);
		objectOutputStream.writeObject(Temp);
		System.out.println("message to server sent");
		//Temp = (Message) objectInputStream.readObject();
		System.out.println();
		//if(Temp.getMessageString().equals("Success")) {
			//user = new User();
			
		//}
		
		isLoggedIn = false;
		loginAttempt = 0;
		//isConnected = false;
		System.out.println("logout success");
		return isLoggedIn;
	}
	
	public String getUserDirectory() throws IOException, ClassNotFoundException {
		System.out.println("login request sending");
		Message userDirectory =  new Message(user.getUserName(), user.getUserName(), "Server",MessageType.DIRECTORY);
		objectOutputStream.writeObject(userDirectory);
		userDirectory = (Message) objectInputStream.readObject();
		
//		String userDirectory = "1,Vansh,OFFLINE\n2,tommy,ONLINE\n3,kat,OFFLINE";
		return userDirectory.getMessageString();
//		return userDirectory;
	}
}
