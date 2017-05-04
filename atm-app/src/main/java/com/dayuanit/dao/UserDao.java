package com.dayuanit.dao;

import java.sql.Connection;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.domain.User;
import com.dayuanit.vo.OpenAccountVO;

public interface UserDao {
	int addUser(User user);

	int updateBalance(@Param("userId") int userId, @Param("oldBalance") int oldBalance, @Param("newBalance") int newBalance);

	User queryUser(@Param("userId") Integer userId,@Param("username") String username);
	
	User queryAccount(@Param("cardNum")String cardNum);
	
	int transferBalance(@Param("cardNum")String cardNum, @Param("oldBalance") int oldBalance, @Param("newBalance") int newBalance);




}
