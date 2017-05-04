<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp" scope="session"
	value="${pageContext.request.servletContext.contextPath}" />


welcome atm system.
<br>
<br>

<html>
<head>
<meta charset="utf-8">
<script type="text/javascript" src="${cp}/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${cp}/css/jquery-ui.min.css">
<script type="text/javascript" src="${cp}/js/jquery-ui.min.js"></script>


</head>


<body>
	<a href="###" onclick="toLogin();">登陆</a>
	<a href="###" onclick="toRegist();">开户</a>

	<div id="loginDiv" title="登陆">
		用户名：<input type="text" id="username4login" name="username4login"><br>
		<br> 密码：<input type="password" id="password4login" name="password4login">
		<br><br><span id="loginMsg" style="color: red;"></span>
	</div>

	<div id="registDiv" title="开户">
		<br> 账号：<br><input type="text" id="username4regist" name="username4regist"><br>
		<br> 密码：<br><input type="password" id= "password4regist" name="password4regist"><br>
		<br> 确认密码：<br><input type="password" id= "confirmPwd" name="confirmPwd"><br>
		<br> 存款金额：<br><input type="text" id = "amount" name="amount" value="0"><br>
		<br>
	</div>
</body>


<script type="text/javascript">
	$(function() {
		$("#loginDiv").dialog({
			resizable : false,
			autoOpen : false,
			height : 300,
			modal : true,
			buttons : {
				"确认" : function() {
					login();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});
	
	$(function() {
		$("#registDiv").dialog({
			resizable : false,
			autoOpen : false,
			height : 420,
			modal : true,
			buttons : {
				"开户" : function() {
					regist();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});

	function toLogin() {
		$("#loginDiv").dialog("open");
	}
	
	function toRegist() {
		$("#registDiv").dialog("open");
	}
	


	function login() {

		if ("" == $("#username").val()) {
			alert("用户名不能为空！");
			return false;
		}

		if ("" == $("#password").val()) {
			alert("密码不能为空！");
			return false;
		}

		$
				.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/login.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						username : $("#username4login").val(),
						password : $("#password4login").val()
					},
					timeout : 5000, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						if (data.success) {
				    		window.location.href='${cp}/user/toUserzoo.do';
				    	} else {
				    		$("#loginMsg").html(data.message);
				    	}
				    },
					error : function(xhr, textStatus) {
						console.log('错误')
						console.log(xhr)
						console.log(textStatus)
					},
					complete : function() {
						console.log('结束')
					}
				})
	}
	
	function regist() {
	

		$
				.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/openAccount.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						username : $("#username4regist").val(),
						password : $("#password4regist").val(),
						confirmPwd:$("#confirmPwd").val(),
							amount:$("#amount").val()
					},
					timeout : 5000, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {	
						alert("开户成功");
						$("#registDiv").dialog("close");
						toLogin();		
					},
		
					
					error : function(xhr, textStatus) {
						console.log('错误');
						console.log(xhr);
						console.log(textStatus);
					},
					complete : function() {
						console.log('结束');
					}
				})
	}
</script>
</html>