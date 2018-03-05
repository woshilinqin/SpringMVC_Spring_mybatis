<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'TradeList.jsp' starting page</title>
</head>
<link rel="stylesheet"
	href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/ajaxLoad.js"></script>
<script type="text/javascript" src="js/HC.js"></script>
<body>
	<div style="border: 1px solid red;width: 100%;height: 500px;">
		<div id="contentDiv" style="margin: 0 auto;">
			<div id="progressDiv" style="display: none;margin: 0 auto;">
				<img alt="" src="img/ajax-loader.gif">
			</div>
			<form action="getTradeList" id="formList">
				<c:forEach items="${list}" var="item">
  			 	${item.orderNo}&emsp;&emsp;&emsp;&emsp;&emsp;${item.typeMemo}<br>
				</c:forEach>
			</form>
			<%@ include file="/WEB-INF/jsp/pageNav.jsp"%>
		</div>
	</div>
</body>
</html>
