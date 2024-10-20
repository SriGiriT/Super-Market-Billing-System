package com.billingsystem.Model;


public class User {
	private String userName;
	private String userID;
    private Long id;
    private String phoneNumber;
    private String password;
    private Role role;
    private String email;
    private double current_credit;

    public enum Role {
        ADMIN, CASHIER, INVENTORY_MANAGER, CUSTOMER
    }

	private int points;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
	public double getCurrent_credit() {
		return current_credit;
	}

	public void setCurrent_credit(double current_credit) {
		this.current_credit = current_credit;
	}

	public User() {
		
	}

	public User(Long id, String phoneNumber, String password, Role role, String email, String userName) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
		this.email = email;
		this.userName = userName;
		this.points = 0;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", phoneNumber=" + phoneNumber + ", password=" + password + ", role=" + role
				+ ", email=" + email + ", user=" + userName + ", points=" + points + "]";
	}
	
	public boolean haveCreditForTransaction(double totalAmount) {
		return this.current_credit - totalAmount >= 0;
	}
	
    
    
}
