package com.dayuanit.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.domain.AccountFlow;
import com.dayuanit.dto.AccountFlowDTO;


public interface AccountFlowDao {

		int addAccountFlow(AccountFlow accountFlow);

	    int countFlow(Map<String, Object> map);
		
		//List<AccountFlow> listAccountFlow(Map<String, Object> map);
		
		List<AccountFlowDTO> listAccountFlow2(Map<String, Object> map);
		
}
