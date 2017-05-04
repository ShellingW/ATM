package com.dayuanit.vo;

import com.dayuanit.domain.User;

public class OpenAccountVO {

	private String username;
	private String password;
	private String confirmPwd;
	private int amount;
	
	public User createUser() {
		User user = new User();
		user.setUsername(username);
		user.setBalance(amount);
		user.setPassword(password);
		return user;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
