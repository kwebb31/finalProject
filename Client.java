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
	private User user = null;
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
	                
	                System.out.println("Client is connected to Server");
	               while(loginAttempt == 0) {
	            	   ;
	               }
	                while(isLoggedIn) {
	                	;
	                }
	                
	                s.close();
	               
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public void login(String user, String pw) {
		//System.out.println("login request sending");
		Message Temp = new Message(Type.Login, user + ":" + pw, );
		objectOutputStream.writeObject(Temp);

		isLoggedIn = true;
		loginAttempt = 1;
	}
	public Message sendMessage() {
		
	}
	public void logout() {
		isLoggedIn = false;
		loginAttempt = 0;
		//isConnected = false;
	}
}