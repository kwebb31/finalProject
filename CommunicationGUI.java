package finalProject;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

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
	private JPanel optionsPanel;
	JButton logout;
	JButton showDirectory;
	JButton createGroup;
	JButton sendNewMessage;
	JButton viewLogs;
	JFrame informationFrame;

	private JPanel listPanel;
	private DefaultListModel<String> chats;
	private JList jlist;
	private JPanel overallPanel;

	
	public CommunicationGUI(Client client) {
		this.client = client;
	}

	public void processCommands() {
		username = JOptionPane.showInputDialog("Enter Your Username");
		password = JOptionPane.showInputDialog("Enter Your Password");
		
		
		setDisplayPanels();
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
		createGroup = new JButton("Create a group");
		sendNewMessage = new JButton("Create a new chat");
		viewLogs = new JButton("View Logs");

		
		frame.getRootPane().setDefaultButton(logout);
		
		// adding all the buttons to our options panel
		optionsPanel.add(logout);
		optionsPanel.add(showDirectory);
		optionsPanel.add(createGroup);
		optionsPanel.add(sendNewMessage);
		optionsPanel.add(viewLogs);

		
		
		// creating another panel to display all the DVDs with grid Layout
		listPanel = new JPanel();
		listPanel.setLayout(new GridLayout());
				 
		// creating a list for the grid Layout
		 chats = new DefaultListModel();
		 
		 
		 // creating a jlist to which our list is passed
		 jlist = new JList(chats);
		 
		 // adding that jlist to the listPanel
		 listPanel.add(jlist);
		 
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

}
