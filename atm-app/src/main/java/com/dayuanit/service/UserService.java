package com.dayuanit.service;


import java.sql.Connection;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.domain.User;
import com.dayuanit.vo.OpenAccountVO;

public interface UserService{

	boolean openAccount(OpenAccountVO openAccountVO);
	
	User queryUser(Integer userId,String username);

	User queryAccount(String cardNum);
	
	boolean deposit( int userId,int amount);
	
	boolean draw(int userId,int amount);
	
	boolean transfer(String inCardNum, String outCardNum, int amount);

	}


