package finalProject;

import java.util.ArrayList;

public class User {

	String userName;
	Integer id;
	static Integer idNumber = 0;
	Role userRole = Role.REGULAR; 
	String userPassword;
	Boolean userIsOnline;
	Boolean userLoginSuccessful;
	ArrayList<String> userChatroomArray = new ArrayList<String>();
	ArrayList<String> userIDList = new ArrayList<String>();
	
	public User(String userName, Role userRole, String userPassword, Boolean userIsOnline, Boolean userLoginSuccessful ) {
		this.userName = userName;
		SetID();
		this.userRole = Role.REGULAR;
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
	public String toString() {
	    return userName + "," + id + "," + userRole + "," + userPassword + "," + userIsOnline + "," + userLoginSuccessful;
	}
	
}
