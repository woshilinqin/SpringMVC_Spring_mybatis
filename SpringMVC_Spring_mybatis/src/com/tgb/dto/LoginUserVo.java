package com.tgb.dto;

import java.util.HashMap;

public class LoginUserVo {
	private static HashMap loginUserMap = new HashMap();
	private static LoginUserVo loginUserVo;

	public static LoginUserVo getVo() {
		if (loginUserVo == null) {
			loginUserVo = new LoginUserVo();
		}
		return loginUserVo;
	}

	public static HashMap getLoginUserMap() {
		return loginUserMap;
	}

}
