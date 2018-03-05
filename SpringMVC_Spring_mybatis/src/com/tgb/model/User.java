package com.tgb.model;



/**
 * 鐢ㄦ埛
 * @author liang
 *
 */
public class User {

	private int id;
	private String age;
	private String userName;
	
	//增加来测试二维码登录
	private String pwd;
	
	public User(){
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public User(int id, String age, String userName) {
		super();
		this.id = id;
		this.age = age;
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", userName=" + userName
				+ ", pwd=" + pwd + "]";
	}
	
	
	
}
