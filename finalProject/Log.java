package finalProject;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Log {
	private ArrayList<String> loggedMessagesArray = new ArrayList<String>();
	
	public boolean isIT(User x) {
		if (x.getRole() == Role.IT) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//method that reads the text file. updates the array of logged messages
	public void updateLoggedMessageArray() {
		//THIS IS WHERE THE FILE NAME SHOULD BE CHANGED
		try (BufferedReader reader = new BufferedReader(new FileReader("commlogs.txt"))){
			String line;
			loggedMessagesArray.clear();
			while((line = reader.readLine()) != null) {
				loggedMessagesArray.add(line);
				}
			}catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
				}
		}
	
	//method that returns string of all logged messages, separated by "\n"
	public String getAllLogs() {
		updateLoggedMessageArray();
		String result = "";
		for (String loggedMessage : loggedMessagesArray) {
			result += (loggedMessage + "\n");
			}
		return result;
		}
	
	//method that returns string of all logged messages with sender "x", separated by "\n"
	public String filterLogsBySender(String x) {
		//StringBuilder class, allows for a mutable sequence of characters
		StringBuilder filteredLoggedMessages = new StringBuilder();
		
		for (String loggedMessage : loggedMessagesArray) {
			// Split the message by comma and extract the sender
			String[] parts = loggedMessage.split(",");
			String messageSender = parts[0].trim().split(" ")[0];
			//System.out.println(messageSender);
			if (x.equalsIgnoreCase(messageSender)) {
				filteredLoggedMessages.append(loggedMessage).append("\n");
            }
		}
		return filteredLoggedMessages.toString();
	}
	
	//String date format is expected to be in "MM.dd.yy" EX: "11.05.23"
	public String filterLogsByDate(String x) {
		StringBuilder filteredLoggedMessages = new StringBuilder();
		
		for (String loggedMessage : loggedMessagesArray) {
			//split message by comma and extract date
			String[] parts = loggedMessage.split("\\)");
			String dateString = parts[1].trim().split(",")[0];
			//System.out.println(dateString);
			if (x.equalsIgnoreCase(dateString)) {
				filteredLoggedMessages.append(loggedMessage).append("\n");
			}
		}
		return filteredLoggedMessages.toString();
	}
	
	//method that writes a message(with info) to log text file.
	//I decided to call date of when a message is logged instead of created. Can be changed later
	public void addMessageToFile(Message message) {
		//get date and time
		Date messageDate = message.getMessageDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yy,HH:mm");
		String formattedDate = dateFormat.format(messageDate);
		//format the string to be logged
		String messageToLog = message.getMessageSender() + "," +  message.getMessageSenderUID() + "(" + message.getMessageReceiver() + ")" 
		+ formattedDate + "," + message.getMessageString();
		//write to file on a new line
		//THIS IS WHERE THE FILE NAME SHOULD BE CHANGED
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("commlogs.txt", true))){
			writer.write(messageToLog);
			writer.newLine();
			System.out.println("Message logged to file successfully.");
		}catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
	
	
	//driver for testing, THIS SHOULD BE DELETED
	public static void main(String[] args) {
        Log log = new Log();
        
        //test getAllLogs
        System.out.println("All messages from textfile:");
        System.out.println(log.getAllLogs());
        
        //test filterLogsBySender
        System.out.println("All messages from sender JOE:\n");
        String filteredMessages = log.filterLogsBySender("JOE");
        System.out.println(filteredMessages);
        
        //test filterLogsByDate
        System.out.println("All messages from date 11.28.23:\n");
        String filteredDates = log.filterLogsByDate("11.28.23");
        System.out.println(filteredDates);
        
        //create message object
        Message sampleMessage = new Message("Hello, how are you?", "Alice", "Bob", MessageType.TEXT, 1001, 1002);
        //call the addMessageFunction
        log.addMessageToFile(sampleMessage);
        System.out.println(log.getAllLogs());
    }

}
