package finalProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CommunicationGUI implements CommunicationUserInterface {

    private String username;
    private String password;
    private Client client;
    private JFrame frame;
    private JFrame groupFrame;
    private JPanel optionsPanel;
    private JButton logout;
    private JButton showDirectory;
    private JButton createGroup;
    private JButton sendNewMessage;
    private JButton viewLogs;
    private JButton sendMessage;
    private JButton refresh;
    private JFrame directoryFrame;
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
    private String[] userDirectory;
    private boolean added = false;
    private JList jlistSelectedUsers;
    private ArrayList<Integer> receiversID;
    private ArrayList<Chat> userChatroomArray = new ArrayList<Chat>();

    public CommunicationGUI(Client client) {
        this.client = client;
        this.counter = 0;
        this.receiversID = new ArrayList<Integer>();
    }

    public void processCommands() {
        if (counter == 0) {
            username = JOptionPane.showInputDialog("Enter Your Username");
            password = JOptionPane.showInputDialog("Enter Your Password");

        } else {
            username = JOptionPane.showInputDialog("Failed Login! Incorrect Details! Try again! \nEnter Your Username");
            password = JOptionPane.showInputDialog("Enter Your Password");
        }

        try {
            if (client.login(username, password)) {
            	startCommunicationThread();
            	setDisplayPanels();
                
            } else {
                counter++;
                processCommands();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void startCommunicationThread() {
        CommunicationManager receive = new CommunicationManager();
        Thread thread = new Thread(receive);
        thread.start();
    }

    private void setDisplayPanels() throws ClassNotFoundException, IOException {
        // Create a new frame
        frame = new JFrame("Communication");
        
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Creating a panel to display options with flow layout
        optionsPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        optionsPanel.setLayout(layout);

        // Initializing all the buttons we need on our option panels
        logout = new JButton("Logout");
        showDirectory = new JButton("Show Directory");
        sendNewMessage = new JButton("Create a new chat");
        createGroup = new JButton("Create a group");
        viewLogs = new JButton("View Logs");
        refresh = new JButton("Refresh");

        frame.getRootPane().setDefaultButton(logout);

        // Writing the actionListener function for each button pressed to perform
        // specific required tasks
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    logout();
                } catch (ClassNotFoundException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        showDirectory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showDirectoryPanel();
                } catch (ClassNotFoundException | IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        sendNewMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	System.out.println("before show directory");
                    frame.dispose();
                	showDirectory();
                    System.out.println("after show directory");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        createGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                try {
                	frame.dispose();
                    createGroup();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        viewLogs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewLogs();
            }
        });
        
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                try {
                	frame.dispose();
                    setDisplayPanels();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Adding all the buttons to our options panel
        optionsPanel.add(logout);
        optionsPanel.add(showDirectory);
        optionsPanel.add(sendNewMessage);
        optionsPanel.add(createGroup);
        if (client.user.getRole() == Role.IT) {
            optionsPanel.add(viewLogs);
        }
        optionsPanel.add(refresh);

        // Creating another panel to display all the DVDs with grid Layout
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout());

        // Creating a list for the grid Layout
        chats = new DefaultListModel();

        userChatroomArray = client.getAllChatRooms();
        System.out.println("before looping through all chatrooms");
        for (int i = 0; i < userChatroomArray.size(); i++) {
        	
        	client.getParticipantsName(client.user.id, userChatroomArray.get(i).participants);
        	while(client.participants == null) {
        		;
        	}
        	chats.addElement(client.participants);
        	client.participants = null;
        }

        // Creating a jlist to which our list is passed
        jlistchats = new JList(chats);

        // Adding that jlist to the listPanel
        listPanel.add(jlistchats);

        // Creating a mouseListener to do different operations for the elements in the list
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Checking if the element is clicked twice, then allowing the user to remove
                // it
                // from there itself, and also enables other options which will be redundant
                // to show if
                // an element is selected
                if (e.getClickCount() == 2) {
                    indexClicked = jlistchats.locationToIndex(e.getPoint());
                    openChat();
                }
            }
        };

        // Creating an overall panel which has a grid layout and adding the optionsPanel
        // and listPanel to it
        jlistchats.addMouseListener(mouseListener);

        overallPanel = new JPanel();
        overallPanel.setLayout(new GridLayout());
        overallPanel.add(optionsPanel);
        overallPanel.add(listPanel);

        // Setting the size of the frame as well as attaching the overallPanel to the
        // frame
        frame.getContentPane().add(overallPanel);
        
    }

    private void logout() throws ClassNotFoundException, IOException {
        //JFrame logoutFrame = new JFrame();
    	frame.dispose();
    	client.logout();
        //logoutFrame.dispose();
        
    }

    private void showDirectoryPanel() throws ClassNotFoundException, IOException, InterruptedException {
        JFrame showDirectoryFrame = new JFrame();

        JPanel directoryPanel = new JPanel();
        directoryPanel.setLayout(new GridLayout());

        JPanel newDirectoryPanel = new JPanel();
        newDirectoryPanel.setLayout(new GridLayout());

        JPanel newOptionsPanel = new JPanel();
        newOptionsPanel.setLayout(new FlowLayout());

        JButton refresh = new JButton("Refresh");

        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDirectoryFrame.dispose();
                try {
                    showDirectoryPanel();
                } catch (ClassNotFoundException | IOException | InterruptedException  e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        Message tempMSG = new Message(client.user.getUserName(), client.user.getUserName(), "Server",MessageType.DIRECTORY);
        client.getUserDirectory(tempMSG);
		while(client.directory == null) {
			;
		}
		String temp = client.directory;
		client.directory = null;
        userDirectory = temp.split("\n");

        userList = new DefaultListModel();

        for (int i = 0; i < userDirectory.length; i++) {
            String[] parse = userDirectory[i].split(",");
            userList.addElement(parse[1] + " - " + parse[2]);
        }

        jlistUsers = new JList(userList);
        jlistUsers.setCellRenderer(new CustomListCellRenderer());
        newDirectoryPanel.add(jlistUsers);
        newOptionsPanel.add(refresh);
        directoryPanel.add(newOptionsPanel);
        directoryPanel.add(newDirectoryPanel);

        showDirectoryFrame.getRootPane().setDefaultButton(refresh);
        showDirectoryFrame.getContentPane().add(directoryPanel);
        showDirectoryFrame.setSize(400, 600);
        showDirectoryFrame.setLocationRelativeTo(null);
        showDirectoryFrame.setVisible(true);
    }

    private void sendNewMessage() throws ClassNotFoundException, IOException, InterruptedException {
        JFrame sendNewMessageFrame = new JFrame();
        showDirectoryPanel();
    }

    private void sendNewMessageToUser() throws IOException {
        JFrame sendNewMessageToUserFrame = new JFrame();
        String inputBox = "Enter message you want to send to ";
        if (receiversID.size() > 1) {
            inputBox += "this Group";
        } else {
            inputBox += userDirectory[indexClicked].split(",")[1];
        }
        String messageToBeSent = JOptionPane.showInputDialog(inputBox);
        client.sendMessage(messageToBeSent, username, userDirectory[indexClicked],
                client.user.getID().toString(), receiversID);
        receiversID.clear();
        System.out.println("done sneding message from gui");
    }

    private void viewLogs() {
        JFrame viewLogsFrame = new JFrame();
        
        viewLogsFrame.setLayout(new GridLayout());   
        JPanel logPanel = new JPanel();
        logPanel.setPreferredSize(new Dimension(600, 600));
        JTextArea logs = new JTextArea(25,40);       
        logs.append(client.viewLog("all"));
        logPanel.add(new JScrollPane(logs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        
        viewLogsFrame.add(logPanel);
        
        viewLogsFrame.setSize(600, 600);
        viewLogsFrame.setLocationRelativeTo(null);
        viewLogsFrame.setVisible(true);
    }

    private void createGroup() throws ClassNotFoundException, IOException, InterruptedException {
        JFrame createGroupFrame = new JFrame();

        receiversID.clear();
        added = false;

        groupFrame = new JFrame();

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
                    e1.printStackTrace();
                }
            }
        });

        newOptionsPanel.add(sendMessage);
        
        JButton goBack = new JButton("Return to main Lobby");
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	createGroupFrame.dispose();
                    setDisplayPanels();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        newOptionsPanel.add(goBack);

        DefaultListModel<String> selectedUserList = new DefaultListModel();

        JButton addPerson = new JButton("Add Person to Group");
        addPerson.setEnabled(false);

        addPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < selectedUserList.getSize(); i++) {
                    if (selectedUserList.elementAt(i).toString().equals(userDirectory[indexClicked].split(",")[1])) {
                        added = true;
                    }
                }

                if (added == false) {
                    selectedUserList.addElement(userDirectory[indexClicked].split(",")[1]);
                    receiversID.add(Integer.parseInt(userDirectory[indexClicked].split(",")[0]));
                    sendMessage.setEnabled(true);
                }

                added = false;
                addPerson.setEnabled(false);
            }
        });

        newOptionsPanel.add(addPerson);
        Message tempMSG2 = new Message(client.user.getUserName(), client.user.getUserName(), "Server",MessageType.DIRECTORY);
        client.getUserDirectory(tempMSG2);
		while(client.directory == null) {
			;
		}
		String temp = client.directory;
		client.directory = null;
        userDirectory = temp.split("\n");

        userList = new DefaultListModel();

        for (int i = 0; i < userDirectory.length; i++) {
            String[] parse = userDirectory[i].split(",");
            userList.addElement(parse[1]);
        }

        jlistUsers = new JList(userList);
        newDirectoryPanel.add(jlistUsers);

        jlistSelectedUsers = new JList(selectedUserList);
        selectedUserPanel.add(jlistSelectedUsers);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
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
        groupFrame.setSize(600, 600);
        groupFrame.setLocationRelativeTo(null);
        groupFrame.setVisible(true);
    }

    private class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Customize the text color based on the condition
            String text = value.toString();
            if (text.contains("ONLINE")) {
                renderer.setForeground(new Color(0, 200, 0));
            } else {
                renderer.setForeground(Color.RED);
            }

            return renderer;
        }
    }

    private void showDirectory() throws ClassNotFoundException, IOException, InterruptedException {
		System.out.println("top of show directory");
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
		
		JButton goBack = new JButton("Return to main lobby");		
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				directoryFrame.dispose();
				try {
					setDisplayPanels();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//		sendMessage.setPreferredSize(new Dimension(20,20));
		newOptionsPanel.add(goBack);
		
		System.out.println("the get user directory call");
		Message tempMSG3 = new Message(client.user.getUserName(), client.user.getUserName(), "Server",MessageType.DIRECTORY);
		client.getUserDirectory(tempMSG3);
		while(client.directory == null) {
			;
		}
		String temp = client.directory;
		client.directory = null;
		System.out.println("directory inside GUI");
		System.out.println(temp);
		userDirectory = temp.split("\n");
		System.out.println("after the directory call");
		
		userList = new DefaultListModel();
		
		for(int i = 0; i < userDirectory.length; i++) {
			String[] parse = userDirectory[i].split(",");
			userList.addElement(parse[1]);
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
		
		
	}
    
    private void openChat() {
        JFrame openChatFrame = new JFrame();

        JPanel chatOverviewPanel = new JPanel();
        chatOverviewPanel.setLayout(new GridLayout());

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout());

        JPanel newOptionsPanel = new JPanel();
        newOptionsPanel.setLayout(new FlowLayout());

        JButton sendMessage = new JButton("Send Message");

        sendMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    sendNewMessageToUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        newOptionsPanel.add(sendMessage);
        
        JButton goBack = new JButton("Return to main lobby");

        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	openChatFrame.dispose();
                try {
                    setDisplayPanels();
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        newOptionsPanel.add(goBack);

        JButton refresh = new JButton("Refresh");

        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openChat();
            }
        });

        newOptionsPanel.add(refresh);

        ArrayList<Message> messages = client.getAllMessages(userChatroomArray.get(indexClicked).chatRoomID);

        DefaultListModel<String> messageList = new DefaultListModel();

        for (int i = 0; i < messages.size(); i++) {
            messageList.addElement(
                    messages.get(i).getMessageString() + " - " + messages.get(i).getMessageSender() + " - " + messages.get(i).getMessageDate().toString());
        }

        JList jlistMessages = new JList(messageList);
        messagePanel.add(jlistMessages);

        chatOverviewPanel.add(newOptionsPanel);
        chatOverviewPanel.add(messagePanel);

        openChatFrame.getRootPane().setDefaultButton(sendMessage);
        openChatFrame.getContentPane().add(chatOverviewPanel);
        openChatFrame.setSize(600, 600);
        openChatFrame.setLocationRelativeTo(null);
        openChatFrame.setVisible(true);
    }
}
