<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加用户</title>
    
	<script type="text/javascript">
	function addUser(){
		var form = document.forms[0];
		form.action = "<%=basePath%>user/addUser";
		form.method="post";
		form.submit();
	}
</script>

  </head>
  
  <body>
    <h1>添加用户</h1>
	<!-- <form action="" name="userForm">
		姓名：<input type="text" name="userName">
		年龄：<input type="text" name="age">
		<input type="button" value="添加" onclick="addUser()">
	</form> -->
   <sf:form name="userForm" modelAttribute="user" method="post" id="formSq">
		姓名：<sf:input type="text" path="userName" value="null"/><br>
			<sf:errors path="userName"></sf:errors><br>
		年龄：<sf:input type="text" path="age" value="null"/><br>
			<sf:errors path="age"></sf:errors><br>
			<input type="button" value="添加" onclick="addUser()">
	</sf:form>
	
  </body>
</html>
