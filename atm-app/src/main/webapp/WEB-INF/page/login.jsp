<html>

<%@page language="java" contentType="text/html; charset=utf-8"%>


<script type="text/javascript" src=${cp}/js/jquery.min.js></script>
<body>
	<h1>登录页面</h1>
	<br>
	<h1 style="color: red">${errorMsg}</h1>

	<form>
		用户名：<input type="text" id="username" name="username">
		<br><br>密码：<input type="password" id="password" name="password">
		<br><br><input type="button" onclick="login();" value="登录">
		<br><br><input type="button" onclick="$('#userInfo').toggle();" value="隐藏/显示">
	</form>
	
		<div id="userInfo">
			<h2>修改个人信息</h2>
			<br> 姓名：<input type="text"><br>
			<br> 年龄：<input type="text"><br>
		</div>
</body>

<script type="text/javascript">
	
	$("#userInfo").hide();

	function login() {
		var pwd = $("#password").val();
		if (pwd.length < 6) {
			alert("密碼長度小於6位")
			return;
		}

				$.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/login.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						username : $("#username").val(),
						password : $("#password").val()
					},
					timeout : 5000, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						window.location.href = '${cp}/user/toUserzoo.do';
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
</script>
<html>