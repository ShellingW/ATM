<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" scope="session" value="${pageContext.request.servletContext.contextPath}"/>

<div>
<h4>用户名：${login_user.username}</h4>
<h4>卡号：${showCardNum}</h4>
<h4>余额：${money}</h4>
</div>