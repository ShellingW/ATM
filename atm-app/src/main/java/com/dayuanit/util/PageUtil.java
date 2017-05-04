package com.dayuanit.util;

public class PageUtil {
	
	public static final int PRE_PAGE_NUM = 3;
	private int currentPage = 1;
	private int startNum = 0;
	private int totalPage = 0;
	
	public PageUtil(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
	public int getTotalPage(int totalNotes) {
		totalPage = (totalNotes % PRE_PAGE_NUM == 0) ? totalNotes/PRE_PAGE_NUM : ((int)(totalNotes/PRE_PAGE_NUM)) + 1;
		return totalPage;
	}
	
	public int getStartNum() {
		startNum = (currentPage - 1) * PRE_PAGE_NUM;
		return startNum;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

}
