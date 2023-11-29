package finalProject;

import java.util.*;
/**
 * 	Program to display and modify a simple DVD collection
 */

public class CommunicationManager {

	public static void main(String[] args) {
		
		CommunicationUserInterface CommInterface;
		Client client = new Client();
		
		CommInterface = new CommunicationGUI(client);
		CommInterface.processCommands();

	}

}
