package com.tgb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgb.dto.ResultBean;
import com.tgb.model.User;
import com.tgb.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	Logger logger=Logger.getLogger(UserController.class);
//	Logger logger1=Logger.getLogger("Mylogger");
//	org.slf4j.Logger logger1=LoggerFactory.getLogger("Mylogger");

	@Autowired
	private UserService userService;
	
	/**
	 * 获取所有用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllUser")
	public String getAllUser(HttpServletRequest request){
		
		List<User> findAll = userService.findAll();
		logger.info("我是通过.class获得的日志1");
		logger.info("我是通过.class获得的日志2");
		logger.info("我是通过.class获得的日志3");
//		logger1.info("我是自定义日志");
		String a="aaa";
		String b="bbb";
		// ★使用{}占位符。避免字符串连接操作，减少String对象（不可变）带来的内存开销,字符串拼接浪费资源
//		logger1.info("hello, my name is{}", "chengyi");
//		logger1.info("hello, i am {}info{}",a,b);
//		logger1.debug("debug, i am {}  {}",a,b);
		logger.error("我是错误");
		request.setAttribute("userList", findAll);
		return "jsp/allUser";
	}
	
	/**
	 * 跳转到添加用户界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddUser")
	public String toAddUser(HttpServletRequest request,ModelMap modelMap){
		modelMap.put("user", new User());
		return "jsp/addUser";
	}
	/**
	 * 添加用户并重定向
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/addUser")
	public String addUser( User user,BindingResult result,HttpServletRequest request,Model model){
		  userService.save(user);
			System.out.println("插入的主键为"+user.getId());
			/**
			 * 类型转换失败时的错误信息输出
			 */
//			if (result.getErrorCount() > 0) {
//				System.out.println("出错了...");
//				for (FieldError error : result.getFieldErrors()) {
//					System.out.println(error.getField() + ":" + error.getDefaultMessage());
//				}
//				// 验证出错，则转向特定的页面（这里是之前的输入的页面）
//				return "addUser";
//			}
		return "redirect:/user/getAllUser";
	}
	
	/**
	 *编辑用户
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(User user,HttpServletRequest request){
		
		
		if(userService.update(user)){
			user = userService.findById(user.getId());
			
			request.setAttribute("user", user);
			return "redirect:/user/getAllUser";
		}else{
			return "/error";
		}
	}
	/**
	 * 根据id查询单个用户
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUser")
	public String getUser(int id,HttpServletRequest request){
		
		request.setAttribute("user", userService.findById(id));
		return "jsp/editUser";
	}
	/**
	 * 删除用户
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delUser")
	public void delUser(int id,HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"error\"}";
		
		if(userService.delete(id)){
			result = "{\"result\":\"success\"}";
		}
		
		response.setContentType("application/json");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
