package com.tgb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgb.dto.LoginUserVo;
import com.tgb.model.User;

@Controller
public class LongConnectionCheck {
	@RequestMapping(value="/check",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String cheStringck(HttpServletRequest request,HttpServletResponse response) {
		String uuid = request.getParameter("uuid");
		response.setContentType("text/html;charset=UTF-8");
		Boolean bool = true;
		String jsonStr = "";
		while (bool) {
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			User user = (User) LoginUserVo.getLoginUserMap().get(uuid);
			if (user != null) {
				bool = false;
				jsonStr = "{\"message\":\"登录成功\"}";
				LoginUserVo.getLoginUserMap().remove(uuid);

			}else{
				jsonStr = "{\"message\":\"查询到还没登录\"}";
			}
			System.out.println(jsonStr);
		}
		return jsonStr;
	}
}
