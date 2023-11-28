import java.util.*;
import java.io.*;

public class Log {
	private ArrayList<String> loggedMessagesArray = new ArrayList<String>();
	
	/* This is probably incorrect. will fix soon
	public boolean isIT(User x) {
		if (x.getRole() == Role.IT) {
			return true;
		}
		else {
			return false;
		}
	}
	*/
	
	//method that reads the text file. updates the array of logged messages
	public void updateLoggedMessageArray() throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("comlog1.txt"))){
			String line;
			loggedMessagesArray.clear();
			while((line = reader.readLine()) != null) {
				loggedMessagesArray.add(line);
				}
			}
		}
	
	//method that returns string of all logged messages, separated by "\n"
	public String getAllLogs() {
		String result = "";
		for (String loggedMessage : loggedMessagesArray) {
			result += (loggedMessage + "\n");
			}
		return result;
		}
	
	//method that returns string of all logged messages with sender "x", separated by "\n"
	public String filterLogsBySenderID(String x) {
		//StringBuilder class, allows for a mutable sequence of characters
		StringBuilder filteredLoggedMessages = new StringBuilder();
		for (String loggedMessage : loggedMessagesArray) {
			// Split the message by comma and extract the sender
			String[] parts = loggedMessage.split(",");
			String messageSender = parts[0].trim().split("\\(")[0];
			if (x.equalsIgnoreCase(messageSender)) {
				filteredLoggedMessages.append(loggedMessage).append("\n");
            }
		}
		return filteredLoggedMessages.toString();
	}
	
	//driver for testing, this should be deleted
	public static void main(String[] args) throws FileNotFoundException, IOException {
        Log log = new Log();
        log.updateLoggedMessageArray();
        
        //test getAllLogs
        System.out.println("All messages from textfile:");
        System.out.println(log.getAllLogs());
        
        //test filterLogsBySender
        System.out.println("All messages from sender Joe:\n");
        String filteredMessages = log.filterLogsBySenderID("JOE");
        System.out.println(filteredMessages);
        
    }

}
