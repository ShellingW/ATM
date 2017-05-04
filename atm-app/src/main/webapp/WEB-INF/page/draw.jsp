<%@page language="java" contentType="text/html; charset=utf-8" %>

<html>
<head>
<meta charset="utf-8">
<script type="text/javascript" src="${cp}/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${cp}/css/jquery-ui.min.css">
<script type="text/javascript" src="${cp}/js/jquery-ui.min.js"></script>
</head>

<body>
<h1>取款页面</h1><br>
<h1 style="color:red">${errorMsg}</h1>

	<a href="###" onclick="toDraw();">取款</a>

	<div id="drawDiv" title="取款">
		<br>用户名：<br><input type="text" id="amount" name="amount"><br>
	</div>

<%-- <form action="${cp}/user/draw.do" method="post"> --%>
<!-- 取出金额：<input type="text" name="amount"> -->
<!-- <input type="submit" value="确认"> -->

<%-- <a href="${cp}/user/toUserzoo.do">返回</a> --%>
<!-- </form> -->
</body>

<script type="text/javascript">

$(function() {
	$("#drawDiv").dialog({
		resizable : false,
		autoOpen : false,
		height : 240,
		modal : true,
		buttons : {
			"确认" : function() {
				draw();
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});
});

function toDraw() {
	$("#drawDiv").dialog("open");
}

function draw() {

	if ("" == $("#amount").val()) {
		alert("取款金额不能为空！");
		return false;
	}


	$
			.ajax({
				url : '${pageContext.request.servletContext.contextPath}/user/draw.do',
				type : 'POST', //GET
				async : true, //或false,是否异步
				data : {
					amount : $("#amount").val()
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
</html>