<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="cache-control" content="no-cache">
<base href="<%=basePath%>">
<title>二维码页面</title>
</head>
<script type="text/javascript" src="<%=path%>/js/jquery-1.7.1.js"></script>

<body>
	<a onclick="getCode();">点击验证码登录</a>
	<div id="divCon" style="display: none;">
		<img alt="" src="" id="QrCodeImg" />
	</div>

</body>

</html>
<script type="text/javascript">
	var uuid;
	function getCode(){
		var timestamp=new Date().getTime();
		 $.ajax({ 
                    type : "POST",  //提交方式  
                    url : "upDateQrCode",//路径  
                    data : {  
                    },
                    success : function(result) {//返回数据根据结果进行相应的处理
						result=eval('('+result+')');  //单个数据才需要使用
                        if (result) {  
                       		 console.log(result);
                       		 uuid = result.uuid;
							$('#QrCodeImg').attr("src",result.url+"?timestamp="+timestamp)
							//轮询
							check();
							
							
                        } else {  
                            console.log(result);
                        }  
                    },
                    error : function(error){
                    console.log(error);
                   } 
                });
		
		$('#divCon').css('display', 'block');
	}
	
	function check(){
		 $.ajax({ 
                    type : "POST",  //提交方式  
                    url : "check",//路径  
                    data : {  
                        uuid: uuid  
                    },
                    success : function(result) {//返回数据根据结果进行相应的处理
						result=eval('('+result+')');  //单个数据才需要使用
                        if ( result ) {  
                       		 console.log(result);
                       		  window.location.href="<%=basePath%>/success"
                        } else {  
                            console.log(result);
                        }  
                    },
                    error : function(error){
                    console.log(error);
                   } 
                });
		
	
	}
</script>

