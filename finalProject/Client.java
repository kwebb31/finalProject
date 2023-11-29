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
	private static  User user = new User();
	private static boolean isLoggedIn;
	private static boolean isConnected;
	private static String host = "localhost";
	private static int port = 1234;
	//private Socket s;
	private static ObjectOutputStream objectOutputStream;
	private static ObjectInputStream objectInputStream;
	private static int loginAttempt = 0;
	public static void main(String[] args) throws ClassNotFoundException {
		
		try (Socket s = new Socket(host,port)) {
					isLoggedIn = false;
					isConnected = true;
	                OutputStream outputStream = s.getOutputStream();
	                objectOutputStream = new ObjectOutputStream(outputStream);
	           
	                InputStream inputStream = s.getInputStream();
	                 objectInputStream = new ObjectInputStream(inputStream);
	                
	                //System.out.println("Client is connected to Server");
	               while(loginAttempt == 0) {
	            	   login("tommy","password1");
	               }
	                while(isLoggedIn) {
	                	;
	                }
	                
	                s.close();
	               
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public static void login(String username, String pw) throws IOException, ClassNotFoundException {
		System.out.println("login request sending");
		Message Temp = new Message(username + ":" + pw,username,"Server",MessageType.LOGIN,0);
		objectOutputStream.writeObject(Temp);
		Temp = (Message) objectInputStream.readObject();
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
	}
	//public Message sendMessage() {
		
	//}
	public void logout() {
		isLoggedIn = false;
		loginAttempt = 0;
		//isConnected = false;
	}
}