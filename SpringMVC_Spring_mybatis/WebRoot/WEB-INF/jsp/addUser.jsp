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
    
    <title>����û�</title>
    
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
    <h1>����û�</h1>
	<!-- <form action="" name="userForm">
		������<input type="text" name="userName">
		���䣺<input type="text" name="age">
		<input type="button" value="���" onclick="addUser()">
	</form> -->
   <sf:form name="userForm" modelAttribute="user" method="post" id="formSq">
		������<sf:input type="text" path="userName" value="null"/><br>
			<sf:errors path="userName"></sf:errors><br>
		���䣺<sf:input type="text" path="age" value="null"/><br>
			<sf:errors path="age"></sf:errors><br>
			<input type="button" value="���" onclick="addUser()">
	</sf:form>
	
  </body>
</html>
