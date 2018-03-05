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
 * @author ����
 * 
 */
@Controller
public class Code {
	/**
	 * �������ɶ�ά�� ֱ��ʹ��io��output��ȥ
	 * �޸�output���Ϳ��Ա��浽��Ŀ���ء�������Ϣ��srcʹ�þ�̬��ͼƬ��
	 * **/
	@RequestMapping(value = "/createQrCode")
	@ResponseBody
	public String createQrCode(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		int uuid = new Random().nextInt();
		String url = "http://test.tunnel.qydev.com/SpringMVC_Spring_mybatis/login?uuid=";
		// logoʵ����ĿӦ�÷ŵ��������棬ʹ��linux�¾���·��
		String logoPath = "D:/Myeclipse2014_workspace/SpringMVC_Spring_mybatis/WebRoot/img/timg.jpg";
//		OutputStream out = response.getOutputStream();
		OutputStream out1 = new FileOutputStream(new File("F:\\apache-tomcat-7.0.52\\webapps\\SpringMVC_Spring_mybatis\\img\\code.jpg"),false);
		QRCodeUtil.encode(url+uuid, logoPath, out1, true);
		out1.close();
		String json="{\"uuid\":"+uuid+",\"url\":\"img/code.jpg\"}";
		return json;
	}

	/**
	 * ���¶�ά������
	 * 
	 * **/
	@RequestMapping(value = "/upDateQrCode")
	public String upDateQrCode(String id, HttpServletRequest request)
			throws Exception {
		// Order order=new Order();
		// order.setId(id);
		// order.setToken(orderToken);
		// orderService.updateToken(order);
		// logger.info("orderid="+id+"Ǯ�����¸����ά��data="+data);
		// request.setAttribute("id", order.getId());
		// request.setAttribute("data", data);
		return "redirect:/createQrCode";
	}

	/**
	 * �����ֻ�����ͼƬ
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
