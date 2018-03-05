package com.tgb.controller;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class ImportExcel {
	@RequestMapping("/UserImport")
	public String toImportPage(){
		return "jsp/UserImport";
	}

	@RequestMapping("import")
	public String upload2(@RequestParam("upload") CommonsMultipartFile upload, HttpSession session) throws Exception {
		// 得到原始的文件名:laodong.jpg
		String fileName = upload.getOriginalFilename();
		String realyPath = session.getServletContext().getRealPath("/upload");// 得到项目文件下的upload路径
		File saveDir = new File(realyPath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File saveFile = new File(saveDir, fileName);
		upload.transferTo(saveFile);

		return "upload/uploadSuccess";

	}
}
