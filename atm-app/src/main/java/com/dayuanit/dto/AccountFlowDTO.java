package com.dayuanit.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountFlowDTO {
	@Override
	public String toString() {
		return "AccountFlowDTO [id=" + id + ", descMsg=" + descMsg + ", createTime=" + createTime + ", amount=" + amount
				+ ", flowDesc=" + flowDesc + "]";
	}
	private int id;
	private String descMsg;
	private Date createTime;
	private int amount;
	private String flowDesc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescMsg() {
		return descMsg;
	}
	public void setDescMsg(String descMsg) {
		this.descMsg = descMsg;
	}
	public String getCreateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getFlowDesc() {
		return flowDesc;
	}
	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}
	
	
	
	
}
