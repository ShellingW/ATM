package com.dayuanit.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.dao.UserDao;
import com.dayuanit.domain.AccountFlow;
import com.dayuanit.domain.User;
import com.dayuanit.exception.ATMBusException;
import com.dayuanit.service.AccountFlowService;
import com.dayuanit.service.UserService;
import com.dayuanit.util.CardNumUtil;
import com.dayuanit.vo.OpenAccountVO;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountFlowService accountFlowService;
	
	@Override
	public boolean openAccount(OpenAccountVO openAccountVO) {			
		//TODO 验证用户是否存在
		User exitUser = userDao.queryUser(null, openAccountVO.getUsername());
		if (null != exitUser) {
			throw new ATMBusException("用户名已存在");
		}		
		User user = new User();
		user.setBalance(openAccountVO.getAmount());
		//TODO 生成卡号
		user.setCardNum(CardNumUtil.create());
		user.setPassword(openAccountVO.getPassword());
		user.setUsername(openAccountVO.getUsername());
		
		int rows = userDao.addUser(user);
		boolean flag = 1 == rows ? true : false;
		if (!flag) {
			throw new ATMBusException("开户失败");
		}
		
			//TODO 是否有存款
		if(openAccountVO.getAmount()>0){
			AccountFlow flow = new AccountFlow();
			flow.setAmount(openAccountVO.getAmount());
			flow.setCardNum(user.getCardNum());
			flow.setDescMsg("开户存钱");
			flow.setFlowId(1000);
			flow.setUserId(user.getId());
			
			flag = accountFlowService.add(flow);
			
			if(!flag){
				throw new ATMBusException("开户存款失败");
			}
		}
			return flag;
	}

	@Override
	public User queryUser(Integer userId, String username) {
		// TODO Auto-generated method stub
		return userDao.queryUser(null,username);
	}

	@Override
	public User queryAccount(String cardNum) {
		// TODO Auto-generated method stub
		User user = userDao.queryAccount(cardNum);
		return user;
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean deposit(int userId,int amount) {
		System.out.println("===========deposit============"); 
		// TODO Auto-generated method stub
		User user= userDao.queryUser(userId, null);

		int oldBalance= user.getBalance();

		int newBalance= oldBalance + amount;

		int rows = userDao.updateBalance(userId,oldBalance,newBalance);
			
		boolean flag = 1 == rows ? true : false;
		if (!flag) {
			throw new ATMBusException("存款失败");
		}
		
		//记录存钱流水
		AccountFlow flow = new AccountFlow();
		flow.setAmount(amount);
		flow.setCardNum(user.getCardNum());
		flow.setDescMsg("存款");
		flow.setFlowId(1000);
		flow.setUserId(user.getId());
		
		flag = accountFlowService.add(flow);
		
		if(!flag){
			throw new ATMBusException("存款失败");
		}
		return flag;
	}

	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean draw(int userId, int amount) {
		System.out.println("===========draw============"); 
		// TODO Auto-generated method stub
		User user= userDao.queryUser(userId, null);
	
		int oldBalance= user.getBalance();

		int newBalance= oldBalance - amount;
		
		int rows = userDao.updateBalance(userId,oldBalance,newBalance);		
		
		boolean flag = 1 == rows ? true : false;
		if (!flag) {
			throw new ATMBusException("取款失败");
		}
		
		//记录存钱流水
		AccountFlow flow = new AccountFlow();
		flow.setAmount(-amount);
		flow.setCardNum(user.getCardNum());
		flow.setDescMsg("取款");
		flow.setFlowId(1001);
		flow.setUserId(user.getId());
		
		flag = accountFlowService.add(flow);
		
		if(!flag){
			throw new ATMBusException("存款失败");
		}
		return flag;
	}

	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean transfer(String inCardNum, String outCardNum, int amount) {
		//TODO 根据卡号查询目标用户
		User outUser= userDao.queryAccount(outCardNum);
		
		System.out.println("amount="+amount);
		System.out.println("inCardNum="+inCardNum);
		System.out.println("outCardNum="+outCardNum);
		
		//TODO 减去自己的金额

		int oldBalanceOut= outUser.getBalance();
		System.out.println("oldBalanceOut="+oldBalanceOut);
		
		int newBalanceOut= oldBalanceOut - amount;
		System.out.println("newBalanceOut="+newBalanceOut);
		
		int rows = userDao.transferBalance(outCardNum,oldBalanceOut,newBalanceOut);		
		
		boolean flag = 1 == rows ? true : false;
		if (!flag) {
			throw new ATMBusException("余额不足");
		}
		
		//记录转账-支出流水
		AccountFlow flow = new AccountFlow();
		flow.setAmount(-amount);
		flow.setCardNum(outUser.getCardNum());
		flow.setDescMsg("转账-支出");
		flow.setFlowId(1002);
		flow.setUserId(outUser.getId());
		
		flag = accountFlowService.add(flow);
		
		if(!flag){
			throw new ATMBusException("转账失败");
		}
				
		//TODO 增加目标金额
		User inUser= userDao.queryAccount(inCardNum);

		int oldBalanceIn= inUser.getBalance();

		int newBalanceIn= oldBalanceIn + amount;

		int rows2 = userDao.transferBalance(inCardNum,oldBalanceIn,newBalanceIn);
			
		boolean flag2 = 1 == rows2 ? true : false;
		
		if (!flag2) {
			throw new ATMBusException("转入失败");
		}
		
		//记录转账-收入流水
		AccountFlow flow2 = new AccountFlow();
		flow2.setAmount(amount);
		flow2.setCardNum(inUser.getCardNum());
		flow2.setDescMsg("转账-收入");
		flow2.setFlowId(1003);
		flow2.setUserId(inUser.getId());
		
		flag = accountFlowService.add(flow2);
		
		if(!flag){
			throw new ATMBusException("转入失败");
		}
	
		return false;
	}
		
}








