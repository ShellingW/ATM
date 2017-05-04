<%@page language="java" contentType="text/html; charset=utf-8" %>


<h1>转账页面</h1><br>
<form action="${cp}/user/transfer.do" method="post">
转出金额：<input type="text" name="amount">
转入账户：<input type="text" name="cardNum">
<input type="submit" value="确认">

<a href="${cp}/user/toUserzoo.do">返回</a>

</form>
