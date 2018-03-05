package com.tgb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.regexp.internal.recompile;

@Controller
public class Test {
	@RequestMapping("/index")
	public String index(){
		return "login/index";
	}
	
	@RequestMapping("/success")
	public String success(){
		return "login/success";
	}
	
}
