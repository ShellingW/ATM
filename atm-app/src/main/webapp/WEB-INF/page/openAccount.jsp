<%@page language="java" contentType="text/html; charset=utf-8" %>

<html>
<title>ATM-开户页面</title>
<body>
<h1>开户页面</h1><br>
<form action="${cp}/user/openAccount.do" method="post">
账号：<input type="text" name="username"><br><br>
密码：<input type="password" name="password"><br><br>
确认密码：<input type="password" name="confirmPwd"><br><br>
存款金额：<input type="text" name="amount" value="0"><br><br>
<input type="submit" value="保存">

</form>

</body>
<html>
