package finalProject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CommunicationGUI implements CommunicationUserInterface{
	
	private String username;
	private String password;
	private Client client;
	private JFrame frame;
	private JFrame groupFrame;
	private JPanel optionsPanel;
	JButton logout;
	JButton showDirectory;
	JButton createGroup;
	JButton sendNewMessage;
	JButton viewLogs;
	JFrame directoryFrame;
	private int counter;
	private JList jlistUsers;
	private JPanel listPanel;
	private DefaultListModel<String> chats;
	private JList jlistchats;
	private JList jlistDirectory;
	private JPanel overallPanel;
	private DefaultListModel<String> userList;
	private String MessageInputMessage;
	private int indexClicked = -1;
//	private String[] userDirectory = {"1,TOMMY,ONLINE", "2,VANSH,OFFLINE", "3,TOMMY1,ONLINE"};	
	private String [] userDirectory;
	boolean added = false;
	JList jlistSelectedUsers;
	ArrayList<Integer> receiversID;

		
	
	public CommunicationGUI(Client client) {
		this.client = client;
		this.counter = 0;
		this.receiversID = new ArrayList<Integer>();
	}
	

	public void processCommands() {
		if(counter == 0) {
			username = JOptionPane.showInputDialog("Enter Your Username");
			password = JOptionPane.showInputDialog("Enter Your Password");
			
		}
		
		else {
			username = JOptionPane.showInputDialog("Failed Login! Incorrect Details! Try again! \nEnter Your Username");
			password = JOptionPane.showInputDialog("Enter Your Password");
		}
		
		
//		setDisplayPanels();
//		
		try {
			if(client.login(username, password)) {
				setDisplayPanels();
			}
			
			else {
				counter++;
				processCommands();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void setDisplayPanels(){
		
		// creating a frame with default close operation
		frame = new JFrame("Communication");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 
		// creating a panel to display options with flow layout
		optionsPanel = new JPanel();
		LayoutManager layout = new FlowLayout();
		optionsPanel.setLayout(layout);
				 
		// initializing all the Buttons we need on our option panels
		logout = new JButton("Logout");
		showDirectory = new JButton("Show Directory");
		sendNewMessage = new JButton("Create a new chat");
		createGroup = new JButton("Create a group");
		viewLogs = new JButton("View Logs");
		
		frame.getRootPane().setDefaultButton(logout);
		
		
		// writing the actionListener function for each button pressed to perform
		// specific required tasks
		 logout.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				try {
					logout();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 	}
		 	});
				 
		showDirectory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					showDirectory();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			});
				 

				 
		 sendNewMessage.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 try {
					sendNewMessage();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 }
			 });
		 
		 createGroup.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 try {
					createGroup();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 }
			 });
				 
		viewLogs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				 viewLogs();
				}
			 });
		
		// adding all the buttons to our options panel
		optionsPanel.add(logout);
		optionsPanel.add(showDirectory);
		optionsPanel.add(sendNewMessage);
		optionsPanel.add(createGroup);
		if(client.user.getRole() == Role.IT) {
			optionsPanel.add(viewLogs);
		}

		
		
		// creating another panel to display all the DVDs with grid Layout
		listPanel = new JPanel();
		listPanel.setLayout(new GridLayout());
				 
		// creating a list for the grid Layout
		 chats = new DefaultListModel();
		 
		 
		 // creating a jlist to which our list is passed
		 jlistchats = new JList(chats);
		 
		 // adding that jlist to the listPanel
		 listPanel.add(jlistchats);
		 
		 // creating an overall panel which has a grid layout and adding the optionsPanel
		 // and listPanel to it
		 overallPanel = new JPanel();
		 overallPanel.setLayout(new GridLayout());
		 overallPanel.add(optionsPanel);
		 overallPanel.add(listPanel);
			 
		 // setting the size of the frame as well as attaching the overallPanel to the frame
		 frame.getContentPane().add(overallPanel);
		 frame.setSize(1200,800);
		 frame.setLocationRelativeTo(null);			 
		 frame.setVisible(true);
		
	}
	
	private void logout() throws ClassNotFoundException, IOException {
		client.logout();
		frame.dispose();
	}
	
	private void showDirectory() throws ClassNotFoundException, IOException {
		receiversID.clear();
		directoryFrame = new JFrame();
//		directoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel directoryOverviewPanel = new JPanel();
		directoryOverviewPanel.setLayout(new GridLayout());
		
		JPanel newDirectoryPanel = new JPanel();
		newDirectoryPanel.setLayout(new GridLayout());
		
		JPanel newOptionsPanel = new JPanel();
		newOptionsPanel.setLayout(new FlowLayout());
		
		JButton sendMessage = new JButton("Send Message");
		sendMessage.setEnabled(false);
		sendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receiversID.add(Integer.parseInt(userDirectory[indexClicked].split(",")[0]));
				System.out.println(receiversID.toString());

				try {
					sendNewMessageToUser();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//		sendMessage.setPreferredSize(new Dimension(20,20));
		newOptionsPanel.add(sendMessage);
		
		String temp = client.getUserDirectory();
		userDirectory = temp.split("\n");

		
		userList = new DefaultListModel();
		
		for(int i = 0; i < userDirectory.length; i++) {
			String[] parse = userDirectory[i].split(",");
			userList.addElement(parse[1] + " - " + parse[2]);
		}
		
		
		jlistUsers = new JList(userList);
		newDirectoryPanel.add(jlistUsers);
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					sendMessage.setEnabled(true);
					indexClicked = jlistUsers.locationToIndex(e.getPoint());
				}
			}
		};
		
		jlistUsers.addMouseListener(mouseListener);
		
		
		directoryOverviewPanel.add(newOptionsPanel);
		directoryOverviewPanel.add(newDirectoryPanel);
		
		directoryFrame.getRootPane().setDefaultButton(sendMessage);		
		directoryFrame.getContentPane().add(directoryOverviewPanel);
		directoryFrame.setSize(600,600);
		directoryFrame.setLocationRelativeTo(null);
		directoryFrame.setVisible(true);
		
		
		
		
		
		
//		directoryFrame.getContentPane().add(directoryOverviewPanel);
//		directoryFrame.setSize(800,600);
//		directoryFrame.setLocationRelativeTo(null);
//		directoryFrame.setVisible(true);
//		
	}
	

	
	private void sendNewMessage() throws ClassNotFoundException, IOException {
		showDirectory();
	}
	
	private void sendNewMessageToUser() throws IOException {
		String inputBox = "Enter message you want to send to ";
		if(receiversID.size() > 1) {
			inputBox += "this Group";
		}
		else {
			inputBox += userDirectory[indexClicked].split(",")[1];
		}
		String messageToBeSent = JOptionPane.showInputDialog(inputBox);
		client.sendMessage(messageToBeSent, username, userDirectory[indexClicked], client.user.getID().toString(), receiversID);
	}

	private void viewLogs() {
	
	}
	
	private void createGroup() throws ClassNotFoundException, IOException {
		
		receiversID.clear();
		
		added = false;
		
		groupFrame = new JFrame();
//		directoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel groupOverviewPanel = new JPanel();
		groupOverviewPanel.setLayout(new GridLayout());
		
		JPanel newDirectoryPanel = new JPanel();
		newDirectoryPanel.setLayout(new GridLayout());
		
		JPanel selectedUserPanel = new JPanel();
		selectedUserPanel.setLayout(new GridLayout());
		
		JPanel newOptionsPanel = new JPanel();
		newOptionsPanel.setLayout(new FlowLayout());
		
		JButton sendMessage = new JButton("Send Message");
		sendMessage.setEnabled(false);
		sendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receiversID.sort(null);
				System.out.println(receiversID.toString());
				try {
					sendNewMessageToUser();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//		sendMessage.setPreferredSize(new Dimension(20,20));
		newOptionsPanel.add(sendMessage);
		
		DefaultListModel<String> selectedUserList = new DefaultListModel();
		
		JButton addPerson = new JButton("Add Person to Group");
		addPerson.setEnabled(false);
		

		addPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < selectedUserList.getSize(); i++) {
					if(selectedUserList.elementAt(i).toString().equals(userDirectory[indexClicked].split(",")[1])) {
						added = true;
					}
				}
				
				if(added == false) {
					selectedUserList.addElement(userDirectory[indexClicked].split(",")[1]);
					receiversID.add(Integer.parseInt(userDirectory[indexClicked].split(",")[0]));
					sendMessage.setEnabled(true);
				}
				
				added = false;
				addPerson.setEnabled(false);
			}
		});
//		sendMessage.setPreferredSize(new Dimension(20,20));
		newOptionsPanel.add(addPerson);
		
		String temp = client.getUserDirectory();
		userDirectory = temp.split("\n");
		
		userList = new DefaultListModel();
		
		for(int i = 0; i < userDirectory.length; i++) {
			String[] parse = userDirectory[i].split(",");
			userList.addElement(parse[1]);
		}
		
		
		jlistUsers = new JList(userList);
		newDirectoryPanel.add(jlistUsers);
		
		jlistSelectedUsers = new JList(selectedUserList);
		selectedUserPanel.add(jlistSelectedUsers);
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					addPerson.setEnabled(true);
					indexClicked = jlistUsers.locationToIndex(e.getPoint());
				}
			}
		};
		
		jlistUsers.addMouseListener(mouseListener);
		
		
		groupOverviewPanel.add(newOptionsPanel);
		groupOverviewPanel.add(newDirectoryPanel);
		groupOverviewPanel.add(selectedUserPanel);


		
		groupFrame.getRootPane().setDefaultButton(sendMessage);		
		groupFrame.getContentPane().add(groupOverviewPanel);
		groupFrame.setSize(600,600);
		groupFrame.setLocationRelativeTo(null);
		groupFrame.setVisible(true);
		
		
	}

}
