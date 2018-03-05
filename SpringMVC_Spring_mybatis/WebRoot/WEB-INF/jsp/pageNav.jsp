<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	.pagination{
		margin:0px;
		float:right;
	}
</style>
<script type="text/javascript">

 function pageQuery(pageNum){
	 
		var url = formList.action;
		var data = serializeObject($("form"));
		data.pageNum = pageNum;
		if(url){
			loadPage(url, data);
		}
		return false;
	}

 
 /**
  * 解决特殊情况先不能翻页问题
  * 将表单序列化成对象
  */
 function serializeObject(form) {
 	var o = {};
 	$.each(form.serializeArray(), function(index) {
 		if (o[this['name']]) {
 			o[this['name']] = o[this['name']] + "," + this['value'];
 		} else {
 			o[this['name']] = this['value'];
 		}
 	});
 	return o;
 } 
</script>



  <div class="pagination">
        <input type="hidden" name="pagenum" id="pagenum">
        <div class="pagination">
				<ul>
				    <!-- 首页 -->
					<li>
					    <c:choose>
						    <c:when test="${pagination.firstPage=='false'}">
						          <a href="javascript:;"  class="disableCss">首页</a>
						    </c:when>
							<c:otherwise> 
						           <a href="javascript:;" onclick="pageQuery(${1})" >首页</a>
						    </c:otherwise>
					    </c:choose>
					 </li>
					 
					<li>
					    <c:choose>
						    <c:when test="${pagination.upPage=='false'}">
						          <a href="javascript:;"  class="disableCss">上一页</a>
						    </c:when>
							<c:otherwise> 
						          <a href="javascript:;" onclick="pageQuery(${pagination.pageNum-1})">上一页</a>
						    </c:otherwise>
					    </c:choose>
					</li>
					
					<!-- 以当前页为中心生成 5个页链接 -->
				<c:if test="${pagination.allRows>0}">
				    <c:forEach var="i" begin="${pagination.beginPage}" end="${pagination.endPage}" step="1"> 
                           
                           <c:choose>
								    <c:when test="${i==pagination.pageNum}"><!-- 当前页显示黑色 -->
								         <li><a href="javascript:;" class="btn-info disableCss" >${i}</a></li>
								    </c:when>
									<c:otherwise> 
								       <li><a href="javascript:;" onclick="pageQuery(${i})">${i}</a></li>
								    </c:otherwise>
							  </c:choose>
				    </c:forEach>
				</c:if>	
				     <li>
					    <c:choose>
						    <c:when test="${pagination.nextPage=='false'}">
						          <a href="javascript:;"  class="disableCss">下一页</a>
						    </c:when>
							<c:otherwise> 
						          <a href="javascript:;" onclick="pageQuery(${pagination.pageNum+1})">下一页</a>
						    </c:otherwise>
					    </c:choose>
					</li>
					<!-- 最后一页 -->
					<li>
					    <c:choose>
						    <c:when test="${pagination.lastPage=='false'}" >
						          <a href="javascript:;"  class="disableCss">末页</a>
						    </c:when>
							<c:otherwise> 
						           <a href="javascript:;" onclick="pageQuery(${pagination.totalPage})">末页</a>
						    </c:otherwise>
					    </c:choose>
					 </li>
					<li>
					    <a href="javascript:;"  class="disableCss"><font color="#000000">共&nbsp;${pagination.allRows}&nbsp;条记录</font></a>
					</li>
					
				</ul>
				 
				    
					
			</div>
      </div>
  



