package com.dayuanit.domain;

import java.util.Date;

public class User {

		private int id;
		private String username;
		private String password;
		private String cardNum;
		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", password=" + password + ", cardNum=" + cardNum
					+ ", balance=" + balance + ", createTime=" + createTime + "]";
		}
		private int balance;
		private Date createTime;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
		public String getCardNum() {
			return cardNum;
		}
		public void setCardNum(String cardNum) {
			this.cardNum = cardNum;
		}
		public int getBalance() {
			return balance;
		}
		public void setBalance(int balance) {
			this.balance = balance;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		} 
}
