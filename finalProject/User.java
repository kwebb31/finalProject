package finalProject;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userName;
	Integer id;
	static Integer idNumber = 0;
	Role userRole = Role.REGULAR; 
	String userPassword;
	Boolean userIsOnline;
	Boolean userLoginSuccessful;
	ArrayList<Chat> userChatroomArray = new ArrayList<Chat>();
	ArrayList<String> userIDList = new ArrayList<String>();
	//Default empty user constructor
	public User() {
		this.userName = null;
		this.userRole = null;
		this.userPassword = null;
		this.userIsOnline = null;
		this.userLoginSuccessful = null;
		SetID();
	}
	// constructor using parameters
	public User(String userName, Role userRole, String userPassword, Boolean userIsOnline, Boolean userLoginSuccessful ) {
		this.userName = userName;
		SetID();
		this.userRole = userRole;
		this.userPassword = userPassword;
		this.userIsOnline = userIsOnline;
		this.userLoginSuccessful = userLoginSuccessful;
	}
	
	private void SetID() {
		idNumber++;
		this.id =  idNumber;
	}
	
	void setPassword(String newPassword) {
		userPassword = newPassword;
	}
	
	void signOut() {
		userIsOnline = false;
		userLoginSuccessful = false;
	}
	void signIn() {
		userIsOnline = true;
		userLoginSuccessful = true;
	}
	
	void setRole(Role newUserRole) {
		userRole = newUserRole;
	}
	
	Boolean getLoginStatus() {
		return userLoginSuccessful;
	}
	Boolean getOnlineStatus() {
		return userIsOnline;
	}
	
	Role getRole() {
		return userRole;
	}
	
	 Integer getID() {
		return id;
	}
	String getPassword() {
		return userPassword;
	}
	
	String getUserName() {
		return userName;
	}
	
	public String toString() {
	    return userName + "," + id + "," + userRole + "," + userPassword + "," + userIsOnline + "," + userLoginSuccessful;
	}
	
}
