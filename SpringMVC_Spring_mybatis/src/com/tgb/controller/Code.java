package com.tgb.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgb.dto.LoginUserVo;
import com.tgb.util.QRCodeUtil;

/**
 * util/BufferedImageLuminanceSource.java util/QRCodeUtil.java
 * Qrcode_swetake.jar zxing-2.2.jar
 * 
 * @author 林钦
 * 
 */
@Controller
public class Code {
	/**
	 * 在线生成二维码 直接使用io流output出去
	 * 修改output流就可以保存到项目本地。返回信息，src使用静态的图片。
	 * **/
	@RequestMapping(value = "/createQrCode")
	@ResponseBody
	public String createQrCode(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		int uuid = new Random().nextInt();
		String url = "http://test.tunnel.qydev.com/SpringMVC_Spring_mybatis/login?uuid=";
		// logo实际项目应该放到配置里面，使用linux下绝对路径
		String logoPath = "D:/Myeclipse2014_workspace/SpringMVC_Spring_mybatis/WebRoot/img/timg.jpg";
//		OutputStream out = response.getOutputStream();
		OutputStream out1 = new FileOutputStream(new File("F:\\apache-tomcat-7.0.52\\webapps\\SpringMVC_Spring_mybatis\\img\\code.jpg"),false);
		QRCodeUtil.encode(url+uuid, logoPath, out1, true);
		out1.close();
		String json="{\"uuid\":"+uuid+",\"url\":\"img/code.jpg\"}";
		return json;
	}

	/**
	 * 更新二维码令牌
	 * 
	 * **/
	@RequestMapping(value = "/upDateQrCode")
	public String upDateQrCode(String id, HttpServletRequest request)
			throws Exception {
		// Order order=new Order();
		// order.setId(id);
		// order.setToken(orderToken);
		// orderService.updateToken(order);
		// logger.info("orderid="+id+"钱包更新付款二维码data="+data);
		// request.setAttribute("id", order.getId());
		// request.setAttribute("data", data);
		return "redirect:/createQrCode";
	}

	/**
	 * 过滤手机表情图片
	 * **/
	public String filterEmoji(String source) {
		if (source != null) {
			Pattern emoji = Pattern
					.compile(
							"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
							Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("");
				return source;
			}
			return source;
		}
		return source;
	}

}
