<html>
<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<head>
	<meta charset="utf-8">
	<script type="text/javascript" src="${cp}/js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${cp}/css/jquery-ui.min.css">
	<link rel="stylesheet" type="text/css" href="${cp}/css/jqpagination.css">
	<script type="text/javascript" src="${cp}/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${cp}/js/jquery.jqpagination.js"></script>
</head>


<body>
<h1>用户中心页面</h1><br>
<jsp:include page="/WEB-INF/page/title/header.jsp" flush="true"/>


<a href="${cp}/user/logout.do">注销</a><br><br>

	<a href="###" onclick="toQuery();">查询</a>
	<a href="###" onclick="toDraw();">取款</a>
	<a href="###" onclick="toDeposit();">存款</a>
	<a href="###" onclick="toTransfer();">转账</a>
	
	<div id="queryDiv" title="查询">
		<br> 开始时间：<br> <input id="startTime" id="startTime" name=startTime value="${startTime}" type="datetime" />
		<br> 结束时间：<br> <input id="endTime" id="endTime" name=endTime value="${endTime}" type="datetime" />
							<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"> 
	</div>
	
	<div>	
		<table id="flows">
		</table>
	</div>


	<div id="drawDiv" title="取款">
		<br>金额：<br><input type="text" id="amount4draw" name="amount4draw"><br>
	</div>

	<div id="depositDiv" title="存款">
		<br>金额：<br><input type="text" id="amount4deposit" name="amount4deposit"><br>
	</div>
	
	<div id="transferDiv" title="转账">
		<br>金额：<br><input type="text" id="amount4transfer" name="amount4transfer"><br>
		<br>转入账户：<br><input type="text" id="cardNum4transfer" name="cardNum4transfer">
	</div>
<br><br><br><br><br><br>
	<div >
			<table id="users"></table>
			<div id="userPagination" class="pagination">
				<a href="#" class="first" data-action="first">&laquo;</a>
			    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
			    <a href="#" class="next" data-action="next">&rsaquo;</a>
			    <a href="#" class="last" data-action="last">&raquo;</a>
			</div>
	</div>
	

<%-- <form id="queryForm" action="${cp}/user/queryFlow.do" method="post"> --%>
<%-- 开始时间：<input id="startTime" name=startTime value="${startTime}" type="datetime" /> --%>
<%-- 结束时间：<input id="endTime"  name=endTime value="${endTime}" type="datetime" /> --%>
<!-- 	<input type="button" onclick="query()" value="查询"/> -->
<%-- 	<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"> --%>
<!-- </form> -->


<form id="exportForm" action="${cp}/user/downloadFlow.do" method="post">
<input id="startTime4Export" name=startTime4Export value="${startTime}" type="hidden" />
<input id="endTime4Export" name=endTime4Export value="${endTime}" type="hidden" />
<input id="currentPage4Export"name="currentPage4Export" value="${currentPage}" type="hidden"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!-- <input type="submit"  value="导出明细"/> -->

</form>


<table>
	<c:forEach var="flow" items="${xxx }">
		<tr>
			<td><c:out value="${flow.id }"/></td>
			<td><c:out value="${flow.amount }"/></td>
			<td><c:out value="${flow.flowDesc }"/></td>
			<td><fmt:formatDate value="${flow.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>		
			<td><c:out value="${flow.descMsg}"/></td>
		</tr>
	</c:forEach>
</table>

<!-- <br><br> -->
<%-- <a href="${cp}/user/downloadFlow.do">導出明細</a><br><br> --%>
<!-- <br><br> -->
<!-- 	<a href="###" onclick="firstPage();">首页</a>&nbsp;&nbsp; -->
<!-- 	<a href="###" onclick="prePage();">上一页</a>&nbsp;&nbsp; -->
<!-- 	<a href="###" onclick="nextPage();">下一页</a>&nbsp;&nbsp; -->
<!-- 	<a href="###" onclick="lastPage();">尾页</a>&nbsp;&nbsp; -->
<!-- 	<input type="text" id="sendPageNum"><a href="###" onclick="sendPageNum();">跳转</a> -->
<!-- 	&nbsp;&nbsp; -->
	
<%-- 	第${currentPage}页&nbsp;/&nbsp;共 ${totalPage}页 --%>
	
<br>
<br>
<jsp:include page="/WEB-INF/page/title/footer.jsp" flush="true"/>
</body>


<script type="text/javascript">

    <c:if test="${not empty totalPage}">
    	var totalPage = ${totalPage};
    </c:if>
	
	function query(){
		var queryForm = document.getElementById("queryForm");
		var cpage = document.getElementById("currentPage");
		cpage.value = 1;
		queryForm.submit();
	}
	
	function nextPage() {
		
		var queryForm = document.getElementById("queryForm");
		var cpage = document.getElementById("currentPage");
		
		if (parseInt(cpage.value) >= parseInt(totalPage) ) {
			return false;
		}
		
		cpage.value = parseInt(cpage.value) + 1;
		queryForm.submit();
	}
	
	function prePage() {
		
		var queryForm = document.getElementById("queryForm");
		var cpage = document.getElementById("currentPage");
		
		if (parseInt(cpage.value) <= 1) {
			return false;
		}
		
		cpage.value = parseInt(cpage.value) - 1;
		queryForm.submit();
	}
	
	function firstPage() {
		var queryForm = document.getElementById("queryForm");
		var cpage = document.getElementById("currentPage");
		cpage.value = 1;
		queryForm.submit();
	}
	
	function lastPage() {
		var queryForm = document.getElementById("queryForm");
		var cpage = document.getElementById("currentPage");
		cpage.value = totalPage;
		queryForm.submit();
	}
	
	function sendPageNum() {
		var queryForm = document.getElementById("queryForm");
		var cpage = document.getElementById("currentPage");
		var sendPageNum = document.getElementById("sendPageNum");
		
		cpage.value = sendPageNum.value;
		queryForm.submit();
	}
	
//===============================查询flow===========================================
	$(function() {
		$("#queryDiv").dialog({
			resizable : false,
			autoOpen : false,
			height : 240,
			modal : true,
			buttons : {
				"确认" : function() {
					currentPage=1;
					query();
					init();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});
	
	
	function toQuery() {
		$("#queryDiv").dialog("open");
	}
	
	
	function query() {

		$
				.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/queryFlow.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						startTime : $("#startTime").val(),
						endTime : $("#endTime").val(),
						currentPage:currentPage
					},
					timeout : 5000, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						
				    	if (data.success) {
				    		var results = data.data.data;
				    	
				    		var flowTable = '<tr><td>ID</td><td>金额</td><td>流水类型</td><td>交易时间</td><td>流水备注</td></tr>';
				    		for (var i=0;i<results.length;i++) {
				    			var result = results[i];			    			 
				    			// var newDate= new Date();			    			
				    			// var time =new Date(result.createTime).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");      
				    			
				    			flowTable += '<tr>';
				    			flowTable += '<td>'+result.id+'</td><td>'+result.descMsg+'</td><td>'+result.createTime+'</td><td>'+result.amount+'</td><td>'+result.flowDesc+'</td></tr>'
				    		}
				    		$("#flows").html(flowTable);
				    		maxPage=data.data.totlePage;
				    	}	
				    	$("#queryDiv").dialog("close");
				  	},
					error : function(xhr, textStatus) {
						console.log('错误')
						console.log(xhr)
						console.log(textStatus)
					},
					complete : function() {
						console.log('结束')
					}
				});
	}
	
//=============================取款==========================
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

		if ("" == $("#amount4draw").val()) {
			alert("取款金额不能为空！");
			return false;
		}


		$
				.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/draw.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						amount : $("#amount4draw").val()
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
				});
	}

	
//=============================存款==========================
	$(function() {
		$("#depositDiv").dialog({
			resizable : false,
			autoOpen : false,
			height : 240,
			modal : true,
			buttons : {
				"确认" : function() {
					deposit();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});

	function toDeposit() {
		$("#depositDiv").dialog("open");
	}

	function deposit() {

		if ("" == $("#amount4deposit").val()) {
			alert("存款金额不能为空！");
			return false;
		}


		$
				.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/deposit.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						amount : $("#amount4deposit").val()
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
				});
	}


//=============================转账==========================
	$(function() {
		$("#transferDiv").dialog({
			resizable : false,
			autoOpen : false,
			height : 300,
			modal : true,
			buttons : {
				"确认" : function() {
					transfer();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});

	function toTransfer() {
		$("#transferDiv").dialog("open");
	}

	function transfer() {

		if ("" == $("#amount4transfer").val()) {
			alert("存款金额不能为空！");
			return false;
		}


		$
				.ajax({
					url : '${pageContext.request.servletContext.contextPath}/user/transfer.do',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						amount : $("#amount4transfer").val(),
						cardNum: $("#cardNum4transfer").val()
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
				});
		
	}	
				
//=========================================分页=========================
var currentPage = 1;
	var maxPage = 30;
	
	function init() {
		$('#userPagination').jqPagination({
		    paged: function(page) {
		    	currentPage = page;
		    	query();
		    },
		    max_page : maxPage,
		    current_page : currentPage
		});
	};
		
</script>


<html>
