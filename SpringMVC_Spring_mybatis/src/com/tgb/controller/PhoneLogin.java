package com.tgb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgb.dto.LoginUserVo;
import com.tgb.model.User;

@Controller
public class PhoneLogin {
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		String username = "linqin";
		String pwd = "123456";
		// TODO ��֤��¼,Ӧ�û�ȡһ���û�����Ϣ
		boolean bool = true;
		System.err.println(uuid);
		
		if (bool) {
			// ����½��Ϣ����map
			User user = (User) LoginUserVo.getLoginUserMap().get(uuid);
			if (user == null) {
				user = new User();
				user.setUserName(username);
				user.setPwd(pwd);
				LoginUserVo.getLoginUserMap().put(uuid, user);
			}
		}

		return uuid;

	}
}
