<%@page language="java" contentType="text/html; charset=utf-8" %>


<h1>存款页面</h1><br>
<form action="${cp}/user/deposit.do" method="post">
存入金额：<input type="text" name="amount">
<input type="submit" value="确认">

<a href="${cp}/user/toUserzoo.do">返回</a>

</form>
