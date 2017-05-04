package com.dayuanit.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.dao.AccountFlowDao;
import com.dayuanit.dao.UserDao;
import com.dayuanit.domain.User;
import com.dayuanit.dto.AccountFlowDTO;


@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-config.xml")
@Transactional("txManager")

public class TestDao2 {
	@Autowired
	private UserDao userDao;
	
	private User user;
//		
//	@Before
//	public void initUser() {
//		user = new User();
//		user.setBalance(100);
//		user.setCardNum("622222222233");
//		user.setPassword("123456");
//		user.setUsername("jack3333");
//	}
//	
//
//	@Commit
//	@Test
//	public void testAddUser() {
//		int row = userDao.addUser(user);
//		assertEquals(1, row);
//	}
//    
//    @Commit
//	@Test
//	public void testQueryUser() {		
//		userDao.addUser(user);	
//		User tmpUser = userDao.queryUser(null, user.getUsername());
//		assertEquals(user.getCardNum(), tmpUser.getCardNum());
//	}
//    
//    @Rollback
//	@Test
//	public void testUpdateBalance() {
//		userDao.addUser(user);		
//		int rows = userDao.updateBalance(user.getId(), 100, 90);
//		assertEquals(1, rows);
//	
//	}
	
	@Autowired
	private AccountFlowDao accountFlowDao;
	@Test
	public void test(){
		Map<String,Object> map =new HashMap<String,Object>();		
		map.put("userId",25);
		map.put("startNum",1);
		map.put("preNum",2);
		map.put("startTimeObj",null);
		map.put("endTimeObj",null);		
		List <AccountFlowDTO> list =accountFlowDao.listAccountFlow2(map);
		assertEquals(2,list.size());

    
	}
	
}
