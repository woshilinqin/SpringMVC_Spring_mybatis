package com.tgb.util;

public class HCPagination {
   
	private int pageNum=1;//当前页
	
	private int pageSize=10;//每页显示记录数
	
	private int allRows=0;//总机记录数
	
	private int totalPage;//总页数
	
	private int startIndex=0;//数据库开始检索位置
	
	private int beginPage;//页面循环生成分页链接开始页
	
	private int endPage;//页面循环生成分页链接结束页
	
	private String upPage="true";//上一页链接是否可用
	
	private String nextPage="true";//下一页链接是否可用
	
	private String firstPage;//第一页 是否显示
	private String lastPage;//最后一页 是否显示
	
	
	
	public HCPagination() {
		super();
		// TODO Auto-generated constructor stub
	}

	//处理分页业务
	public void paGing(Integer allRows){
		
		//总页数
		if(allRows%pageSize==0){
			totalPage=allRows/pageSize;
		}else{
			totalPage=allRows/pageSize+1;
		}
		
		//页面循环生成分页链接开始和结束页  以当前页为中心生成5个链接
		/*总页数小于5：
		 * 首页和末页链接不可用 
		 * 生成链接开始数为 1至总页数
		 * */
		if(allRows<1){
			upPage="false";//上一页链接是否可用
			nextPage="false";//下一页链接是否可用
			firstPage="false";//第一页 是否显示
			lastPage="false";//最后一页
		}else if(totalPage<=5){
			if(pageNum==1){  
				firstPage="false";//首页 不可用
				upPage="false";//上一页 不可以用
			}
			if(pageNum==totalPage){ 
				lastPage="false";//末页不可
				nextPage="false";//下一页不可用
			}
			beginPage=1;
			endPage=totalPage;
		}else if(totalPage>=5&&pageNum==1){
			firstPage="false";//首页 不可用
			upPage="false";//上一页 不可以用
			lastPage="true";//末页可用
			nextPage="true";//下一页可用
			beginPage=pageNum;
			endPage=5;
		}else if((totalPage>=5)&&(totalPage-pageNum)==2){
			/*当前页为最后第三页：
			 * 生成链接开始数为 当前页-2，结束页为 当前页+2   
			 * */
			beginPage=pageNum-2;
			endPage=totalPage;
		}else if((totalPage>=5)&&(totalPage-pageNum)==1){
			/*当前页为最后第二页：
			 * 生成链接开始数为 当前页-3，结束页为总页数  
			 * */
			beginPage=pageNum-3;
			endPage=totalPage;
		}else if((totalPage>=5)&&(totalPage==pageNum)){
			/*当前页为最后一页：
			 * 生成链接开始数为 当前页-4，结束页为 当前页   末页链接不可用 下一页链接不可用
			 * */
			lastPage="false";
			nextPage="false";
			beginPage=pageNum-4;
			endPage=pageNum;
		}else{
			if(pageNum>2){
				beginPage=pageNum-2;
				endPage=pageNum+2;
			}else{
				beginPage=1;
				endPage=pageNum+3;
			}
			
		}
		
		
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		////处理分页
		//paGing(this.pageNum);
		//数据库开始索引位置
		this.startIndex=(pageNum-1)*pageSize;
	}

	public Integer getAllRows() {
		return allRows;
	}

	public void setAllRows(Integer allRows) {
		this.allRows = allRows;
		//处理分页
		paGing(this.allRows);
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(Integer beginPage) {
		this.beginPage = beginPage;
	}

	public Integer getEndPage() {
		return endPage;
	}

	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}

	public String getUpPage() {
		return upPage;
	}

	public void setUpPage(String upPage) {
		this.upPage = upPage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}
	
	
}
