package com.dayuanit.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dayuanit.domain.User;
import com.dayuanit.dto.AccountFlowDTO;
import com.dayuanit.dto.AjaxResultDTO;
import com.dayuanit.exception.ATMParamException;
import com.dayuanit.service.AccountFlowService;
import com.dayuanit.service.UserService;
import com.dayuanit.util.CSVUtil;
import com.dayuanit.util.PageUtil;

import com.dayuanit.vo.OpenAccountVO;

@Controller
@RequestMapping("/user")
public class UserController {
	public static final String LOGIN_FLAG = "login_user";

	@Autowired
	private UserService userService;

	@Autowired
	private AccountFlowService accountFlowService;

	public UserController() {
		System.out.println("===========UserController============");
	}

	@RequestMapping("/toUserzoo")
	public String toUserzoo(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("===========toUserzoo============");
		HttpSession session = req.getSession();
		Object attribute = session.getAttribute(LOGIN_FLAG);
		if (null == attribute) {
			return "forward:/WEB-INF/page/login.jsp";
		}
		// 查询
		User user = userService.queryAccount(((User) attribute).getCardNum());

		// 隐藏卡号
		String cardNum = user.getCardNum();
		String showCardNum = cardNum.substring(0, 3) + "**********" + cardNum.substring(cardNum.length() - 2);
		session.setAttribute("showCardNum", showCardNum);

		// 格式化金额
		int balance = user.getBalance();
		NumberFormat nf = NumberFormat.getInstance();
		String money = nf.format(balance);
		session.setAttribute("money", money);

		session.setAttribute(LOGIN_FLAG, user);
		return "forward:/WEB-INF/page/userzoo.jsp";
	}

	@RequestMapping("/toOpenAccount")
	public String toOpenAccount() {
		return "/WEB-INF/page/openAccount.jsp";
	}

	@RequestMapping("/openAccount")
	@ResponseBody
	public boolean openAccount(OpenAccountVO openAccountVO, HttpServletRequest req) {
		try {
			if (null == openAccountVO.getPassword() || "".equals(openAccountVO.getPassword())) {
				throw new ATMParamException("密码不可以为空");
			}
			if (null == openAccountVO.getUsername() || "".equals(openAccountVO.getUsername())) {
				throw new ATMParamException("用户名不可以为空");
			}
			if (null == openAccountVO.getConfirmPwd() || "".equals(openAccountVO.getConfirmPwd())) {
				throw new ATMParamException("确认密码不可以为空");
			}
			if (!openAccountVO.getPassword().equals(openAccountVO.getConfirmPwd())) {
				throw new ATMParamException("密码不相等");
			}

		} catch (Exception ae) {
			ae.printStackTrace();
			req.setAttribute("error", ae.getMessage());
			return false;
		}
		
		boolean flag = userService.openAccount(openAccountVO);
		String page = flag ? "/WEB-INF/page/login.jsp" : "/error.jsp";
		return true;

		
		

	}

	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("===========toLogin============");
		return "forward:/WEB-INF/page/login.jsp";
	}

	@RequestMapping("/login")
	@ResponseBody
	public AjaxResultDTO login(HttpServletRequest req, HttpServletResponse resp) {

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		User loginUser = userService.queryUser(null, username);
		try {
			if (null == loginUser) {
				throw new ATMParamException("用户不存在或密码错误");
			}

			if (!loginUser.getPassword().equals(password)) {
				throw new ATMParamException("用户不存在或密码错误");
			}
		} catch ( Exception e) {
			e.printStackTrace();
			return AjaxResultDTO.failed(e.getMessage());
		}

		HttpSession session = req.getSession();
		session.setAttribute(LOGIN_FLAG, loginUser);

		return AjaxResultDTO.success();
		// return "forward:/WEB-INF/page/userzoo.jsp";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession(false);
		if (null == session) {
			return "redirect:/";
		}
		session.removeAttribute(LOGIN_FLAG);
		return "/";
	}

	@RequestMapping("/toQuery")
	public String toQuery(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		Object loginFlag = session.getAttribute(LOGIN_FLAG);
		if (null == loginFlag) {
			return "/WEB-INF/page/login.jsp";
		}
		return "/WEB-INF/page/queryAccount.jsp";
	}

	@RequestMapping("/queryAccount")
	public String queryAccount(HttpServletRequest req, HttpServletResponse resp) {
		User user = (User) req.getSession().getAttribute("login_user");
		String cardNum = user.getCardNum();
		req.setAttribute("balance", userService.queryAccount(cardNum).getBalance());
		return "forward:/WEB-INF/page/queryAccount.jsp";
	}

	@RequestMapping("/toDeposit")
	public String toDeposit(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("===========toDeposit============");
		HttpSession session = req.getSession();
		Object loginFlag = session.getAttribute(LOGIN_FLAG);
		if (null == loginFlag) {
			return "/WEB-INF/page/login.jsp";
		}
		return "/WEB-INF/page/deposit.jsp";
	}

	@RequestMapping("/deposit")
	@ResponseBody
	public boolean deposit(HttpServletRequest req, HttpServletResponse resp) {

		User user = (User) req.getSession().getAttribute("login_user");

		int userId = user.getId();
		int amount = Integer.parseInt(req.getParameter("amount"));

		userService.deposit(userId, amount);

		return true;
	}

	@RequestMapping("/toDraw")
	public String toDraw(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("===========toDraw============");
		HttpSession session = req.getSession();
		Object loginFlag = session.getAttribute(LOGIN_FLAG);
		if (null == loginFlag) {
			return "/WEB-INF/page/login.jsp";
		}
		return "/WEB-INF/page/draw.jsp";
	}

	@RequestMapping("/draw")
	@ResponseBody
	public boolean draw(HttpServletRequest req, HttpServletResponse resp) {

		User user = (User) req.getSession().getAttribute("login_user");

		int userId = user.getId();
		int amount = Integer.parseInt(req.getParameter("amount"));

		String cardNum = user.getCardNum();
		int balance = userService.queryAccount(cardNum).getBalance();
		System.out.println(balance);

		try {
			if (amount >= balance) {
				throw new ATMParamException("金额不合法");
			}
		} catch (ATMParamException e) {
			req.setAttribute("errorMsg", e.getMessage());
			return false;
		}

		userService.draw(userId, amount);

		return true;
	}

	@RequestMapping("/queryFlow")
	@ResponseBody
	public  AjaxResultDTO queryFlow(HttpServletRequest req, HttpServletResponse resp) {
		// TODO 页面跳转
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		//
		// String startTimeObj =format.format(req.getParameter("startTime"));
		// String endTimeObj =format.format(req.getParameter("endTime"));

		String startTimeObj = (String) req.getParameter("startTime");
		String endTimeObj = (String) req.getParameter("endTime");
		if ("".equals(startTimeObj)) {
			startTimeObj = null;
		}
		if ("".equals(endTimeObj)) {
			endTimeObj = null;
		}

		System.out.println("st=" + startTimeObj);
		User user = (User) req.getSession().getAttribute("login_user");
		
		System.out.println("User+++++++++++++"+user);

		Object pageObj = req.getParameter("currentPage");

		int currentPage = (null == pageObj || "".equals(pageObj.toString())) ? 1 : Integer.parseInt(pageObj.toString());

		System.out.println(" currentPage=" + currentPage);
		PageUtil pageUtil = new PageUtil(currentPage);

		String cardNum = user.getCardNum();
		System.out.println("cardNum=" + cardNum);

		int totalFlow = accountFlowService.countFlow(cardNum, startTimeObj, endTimeObj);

		int totalPage = pageUtil.getTotalPage(totalFlow);

		User loginUser = (User) req.getSession().getAttribute(UserController.LOGIN_FLAG);

		// TODO 查询

		// List<AccountFlow> list =
		// accountFlowService.queryAccountFlow(startTimeObj,endTimeObj,pageUtil,
		// loginUser.getId());
		List<AccountFlowDTO> list = accountFlowService.queryAccountFlow2(startTimeObj, endTimeObj, pageUtil,
				loginUser.getId());
		for (AccountFlowDTO af : list) {
			System.out.println("descmsg"+af.getDescMsg());
		}

		req.setAttribute("xxx", list);

		System.out.println("totalFlow=" + totalFlow);
		System.out.println("totalPage=" + totalPage);

		req.setAttribute("startTime", startTimeObj);
		req.setAttribute("endTime", endTimeObj);

		req.setAttribute("currentPage", currentPage);
		req.setAttribute("totalPage", totalPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("totlePage", totalPage);
		map.put("data", list);
		
		//System.out.println("==========MAP======="+map);

		return AjaxResultDTO.success(map);
	}

	@RequestMapping("/toTransfer")
	public String toTransfer(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("===========toTransfer============");
		HttpSession session = req.getSession();
		Object loginFlag = session.getAttribute(LOGIN_FLAG);
		if (null == loginFlag) {
			return "/WEB-INF/page/login.jsp";
		}
		return "/WEB-INF/page/transfer.jsp";
	}

	@RequestMapping("/transfer")
	@ResponseBody
	public boolean transfer(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("===========transfer============");

		User user = (User) req.getSession().getAttribute("login_user");

		//
		String outCardNum = user.getCardNum();
		String inCardNum = req.getParameter("cardNum");
		int amount = Integer.parseInt(req.getParameter("amount"));

		int balance = userService.queryAccount(outCardNum).getBalance();

		try {
			if (amount >= balance) {
				throw new ATMParamException("金额不合法");
			}
		} catch (ATMParamException e) {
			req.setAttribute("errorMsg", e.getMessage());
			return false;
		}
		userService.transfer(inCardNum, outCardNum, amount);
		return true;
	}

//創建CVS文件	
	@RequestMapping("/downloadFlow")
	public String downloadFlow(CSVUtil csvUtil, HttpServletRequest req, HttpServletResponse resp) {
		// TODO 页面跳转		
		Object pageObj = req.getParameter("currentPage");
		int currentPage = (null == pageObj || "".equals(pageObj)) ? 1 : Integer.parseInt(pageObj.toString());
		User loginUser = (User) req.getSession().getAttribute(LOGIN_FLAG);
		User user = userService.queryUser(null,loginUser.getUsername());

		String cardNum = user.getCardNum();
		System.out.println("卡號"+cardNum);
		int userId = user.getId();
		
		String startTimeObj = (String) req.getParameter("startTime4Export");
		String endTimeObj = (String) req.getParameter("endTime4Export");		
		if ("".equals(startTimeObj)) {
			startTimeObj = null;
		}
		if ("".equals(endTimeObj)) {
			endTimeObj = null;
		}
		System.out.println("起止時間"+startTimeObj+endTimeObj);
		
		
		PageUtil pageUtil = new PageUtil(currentPage);
		int totalFlow = accountFlowService.countFlow(cardNum, startTimeObj, endTimeObj);
		int totalPage = pageUtil.getTotalPage(totalFlow);
		List<Map<String, Object>> exportData = new ArrayList<Map<String, Object>>();

		//System.out.println("頁面"+pageUtil.getCurrentPage()+" "+pageUtil.getStartNum()+" "+pageUtil.getTotalPage(totalFlow));
		
		List<AccountFlowDTO> list = accountFlowService.exportAccountFlow(cardNum,startTimeObj, endTimeObj, pageUtil,userId);
		System.out.println("list is null ?"+list.isEmpty());
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = null;
		for (AccountFlowDTO af : list) {
			createTime = sdf.format(af.getCreateTime());
			Map<String, Object> row1 = new LinkedHashMap<String, Object>();
			row1.put("1", af.getId());
			row1.put("2", af.getAmount());
			row1.put("3", af.getFlowDesc());
			row1.put("4", createTime);
			row1.put("5", af.getDescMsg());
			exportData.add(row1);
		}
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "ID");
		map.put("2", "金额");
		map.put("3", "类型");
		map.put("4", "时间");
		map.put("5", "备注");

		
		HttpSession session = req.getSession();
		String path = session.getServletContext().getRealPath("/"); 
		path = path.replaceAll("\\\\", "/");
		path += "download/";
		System.out.println(path);
		
		String fileName = user.getUsername();
		
		CSVUtil.createCSVFile(exportData, map, path, fileName);
		
		
		
//		try {
//			CSVUtil.exportFile(resp, path, fileName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
//		HttpSession session = req.getSession();
//		String path = session.getServletContext().getRealPath("/"); 
//		System.out.println("path="+path);
//		path = path.replaceAll("\\\\", "/");
//		path += "download\\" + user.getUsername() + ".csv";
//		System.out.println(path);
//		String fileName = user.getUsername();
//		System.out.println("filename="+fileName);
//		CSVUtil.createCSVFile(exportData, map, path, fileName);

//		resp.setCharacterEncoding("utf-8");
//		resp.setHeader("Content-Disposition", "attachment;filename="+user.getUsername()+".csv");
		
		return "/WEB-INF/page/userzoo.jsp";
	}
	
	
//	@RequestMapping("/exportFlow")
//	public String exportFlow(HttpServletResponse resp,HttpServletRequest req, String csvFilePath, String fileName) {
//		
//		HttpSession session = req.getSession();
//		String path = session.getServletContext().getRealPath("/"); 
//		path = path.replaceAll("\\\\", "/");
//		path += "download/";
//		
//		
//		try {
//			CSVUtil.exportFile(resp, path, fileName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		return "/WEB-INF/page/userzoo.jsp";	
//	}
}
