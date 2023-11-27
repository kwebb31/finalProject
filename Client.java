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
	User user = null;
	static boolean isLoggedIn;
	static boolean isConnected;
	static String host = "localhost";
	static int port = 1234;
	public static void main(String[] args) throws ClassNotFoundException {
		
		try (Socket s = new Socket(host,port)) {
					isLoggedIn = false;
					isConnected = true;
	                OutputStream outputStream = s.getOutputStream();
	                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
	           
	                InputStream inputStream = s.getInputStream();
	                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
	                
	                System.out.println("Client is connected to Server. Select an Option: ");
	                Scanner sc = new Scanner(System.in);
	                do {
	                	
	                }while (isConnected == true);
	                
	                

		}catch(IOException e){
			e.printStackTrace();
		}
	}
}