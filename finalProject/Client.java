package finalProject;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
	private User user = new User();
	private boolean isLoggedIn;
	private boolean isConnected;
	private String host = "localhost";
	private int port = 1234;
	private Socket s;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private int loginAttempt = 0;
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
		Message Temp = new Message(username + ":" + pw,username,"Server",MessageType.LOGIN,0);
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
	public void sendMessage(String msg, String sender, String receiver, String senderUID, String receiverUID ) throws IOException {
		String[] parse = receiver.split(",");
		String[] parse2 = receiverUID.split(",");
		int count = 0;
		for(String s : parse) {
			Message Temp = new Message(msg, sender, s, MessageType.TEXT, Integer.parseInt(senderUID) ,Integer.parseInt(parse2[count]));
			count++;
			objectOutputStream.writeObject(Temp);
		}
	}
	public void receiveMessage() throws ClassNotFoundException, IOException {
		Message Temp = (Message) objectInputStream.readObject();
		
	}
	public Boolean logout() throws IOException, ClassNotFoundException {
		Message Temp = new Message("",user.userName,"Server",MessageType.LOGOUT,0);
		objectOutputStream.writeObject(Temp);
		Temp = (Message) objectInputStream.readObject();
		if(Temp.getMessageString().equals("Success")) {
			user = new User();
		}
		isLoggedIn = false;
		loginAttempt = 0;
		//isConnected = false;
		return isLoggedIn;
	}
	
	public String getUserDirectory() throws IOException, ClassNotFoundException {
		System.out.println("login request sending");
		Message userDirectory =  new Message(user.getUserName(), user.getUserName(), "Server",MessageType.DIRECTORY,0);
		objectOutputStream.writeObject(userDirectory);
		userDirectory = (Message) objectInputStream.readObject();
		return userDirectory.getMessageString();
	}
}
