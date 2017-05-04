package com.dayuanit.serviceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuanit.dao.AccountFlowDao;
import com.dayuanit.dao.UserDao;
import com.dayuanit.domain.AccountFlow;
import com.dayuanit.domain.User;
import com.dayuanit.dto.AccountFlowDTO;
import com.dayuanit.service.AccountFlowService;
import com.dayuanit.util.PageUtil;

@Service
public class AccountFlowServiceImpl implements AccountFlowService{

	@Autowired
	private AccountFlowDao accountFlowDao;
	
	@Override
	public boolean add(AccountFlow accountFlow) {	
			int rows =  accountFlowDao.addAccountFlow(accountFlow);
			return rows == 1 ? true : false;
	
	}
	
	@Override
	public int countFlow(String cardNum,String startTimeObj, String endTimeObj) {
//		System.out.println("cn="+cardNum);
//		System.out.println( "st="+startTimeObj);
//		System.out.println("et="+endTimeObj);
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cardNum", cardNum);
		map.put("startTimeObj", startTimeObj);
		map.put("endTimeObj", endTimeObj);
		
		int count= accountFlowDao.countFlow(map);
		
		return count ;
			
	
	}

	@Override
	public int queryAccountFlow(String cardNum) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public List<AccountFlow> queryAccountFlow(String startTimeObj, String endTimeObj,PageUtil pageUtil, int userId) {
//		if (pageUtil.getCurrentPage() < 1 || pageUtil.getCurrentPage() >= Integer.MAX_VALUE) {
//			return Collections.EMPTY_LIST;
//		}
//		
//		int startNum = pageUtil.getStartNum();
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("startNum", startNum);
//		map.put("preNum", PageUtil.PRE_PAGE_NUM);
//		map.put("userId", userId);
//		map.put("startTimeObj", startTimeObj);
//		map.put("endTimeObj", endTimeObj);
//
//		return accountFlowDao.listAccountFlow(map);
//	}

	@Override
	public List<AccountFlowDTO> queryAccountFlow2(String startTimeObj, String endTimeObj, PageUtil pageUtil,
			int userId) {
		// TODO Auto-generated method stub
		if (pageUtil.getCurrentPage() < 1 || pageUtil.getCurrentPage() >= Integer.MAX_VALUE) {
			return Collections.EMPTY_LIST;
		}
		
		int startNum = pageUtil.getStartNum();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("preNum", PageUtil.PRE_PAGE_NUM);
		map.put("userId", userId);
		map.put("startTimeObj", startTimeObj);
		map.put("endTimeObj", endTimeObj);

		return accountFlowDao.listAccountFlow2(map);
	
		
	}

	@Override
	public List<AccountFlowDTO> exportAccountFlow(String cardNum,String startTimeObj, String endTimeObj, PageUtil pageUtil,
			int userId) {
		// TODO Auto-generated method stub
		if (pageUtil.getCurrentPage() < 1 || pageUtil.getCurrentPage() >= Integer.MAX_VALUE) {
			return Collections.EMPTY_LIST;
		}
		
		//int startNum = pageUtil.getStartNum();
		System.out.println("cardNum="+cardNum);
		System.out.println("startTimeObj="+startTimeObj);
		System.out.println("endTimeObj="+endTimeObj);
		
		int totalCountFlow = countFlow(cardNum, startTimeObj, endTimeObj);
		System.out.println("totalCountFlow="+totalCountFlow);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", 0);
		map.put("preNum",totalCountFlow);
		map.put("userId", userId);
		map.put("startTimeObj", startTimeObj);
		map.put("endTimeObj", endTimeObj);

		return accountFlowDao.listAccountFlow2(map);
	
	}




}
