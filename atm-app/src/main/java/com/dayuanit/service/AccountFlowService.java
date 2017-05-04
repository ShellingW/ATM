package com.dayuanit.service;

import java.util.List;

import com.dayuanit.domain.AccountFlow;
import com.dayuanit.dto.AccountFlowDTO;
import com.dayuanit.util.PageUtil;


public interface AccountFlowService {
	boolean add(AccountFlow accountFlow);

	int countFlow(String cardNum,String startTimeObj, String endTimeObj);
	
	int queryAccountFlow(String cardNum);

	//List<AccountFlow> queryAccountFlow( String startTimeObj, String endTimeObj, PageUtil pageUtil,int userId);

	List<AccountFlowDTO> queryAccountFlow2(String startTimeObj, String endTimeObj, PageUtil pageUtil,int userId);
	

	List<AccountFlowDTO> exportAccountFlow(String cardNum, String startTimeObj, String endTimeObj, PageUtil pageUtil,
			int userId);

	
	

}
