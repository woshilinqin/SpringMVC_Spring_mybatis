package com.tgb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tgb.model.Trade;
import com.tgb.service.ITradeService;

@Controller
public class LoadExcel {
	private Logger logger = Logger.getLogger(LoadExcel.class);

	@Resource
	private ITradeService tradeService;

	@SuppressWarnings("resource")
	@RequestMapping("/transReport")
	public void transReport(String startDate, String endDate,
			HttpServletResponse response, HttpSession session) throws Exception {
		logger.info("【导出账单明细报表开始】beginDate=" + startDate + ",endDate="
				+ endDate);
		// 拼装查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		List<Trade> list = tradeService.findAll(paramMap);

		HSSFWorkbook wb = new HSSFWorkbook();
		/* 报表字体 */
		Font fontColumn = wb.createFont();
		fontColumn.setFontName("宋体");
		fontColumn.setFontHeightInPoints((short) 12);// 设置字体大小
		fontColumn.setBold(true);

		// 居中样式
		CellStyle styleColumn = wb.createCellStyle();
		styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		styleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleColumn.setFont(fontColumn);

		// 两位小数点样式
		CellStyle formatStyle = wb.createCellStyle();
		formatStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
		formatStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		DataFormat format = wb.createDataFormat();
		formatStyle.setDataFormat(format.getFormat("0.00"));

		String str_name = "";

		int sheetNum = 1;// 当前的sheet索引;
		int recNum = 0;// 当前list的遍历的记录索引
		int sheetNnum = 30000; // 设置一sheet的大小
		while (true) {
			int rowNum = 0;  //行
			// 报表头内容，占1行
			HSSFSheet ws = wb.createSheet("第"+sheetNum+"页");
			sheetNum++;
			HSSFRow rowColumn0 = ws.createRow(0); // 创建行
			rowColumn0.createCell(0).setCellValue("序号");
			rowColumn0.createCell(1).setCellValue("订单号");
			rowColumn0.createCell(2).setCellValue("交易类型");

			for (int i = 0; i <= 2; i++) {
				rowColumn0.getCell(i).setCellStyle(styleColumn);
			}
			
			int sheetRecNum = 0;  //当前sheet记录行数;
			for (int i = recNum; i < list.size(); i++,recNum++, rowNum++, sheetRecNum++) {
				//单个SHEET数据行超过SHEET应达到数据限额，则停止写入  
                if(sheetRecNum >= sheetNnum) break;
				Trade trade = list.get(i);
				HSSFRow rowBody = ws.createRow(sheetRecNum + 1);
				rowBody.createCell(0).setCellValue(sheetRecNum + 1);
				rowBody.createCell(1).setCellValue(trade.getOrderNo());
				rowBody.createCell(2).setCellValue(trade.getTypeMemo());

				rowBody.getCell(0).setCellStyle(styleColumn);
			}
			
			HSSFRow rowEnd = ws.createRow(sheetRecNum + 1);
			rowEnd.createCell(3).setCellValue("合计");
			rowEnd.createCell(4).setCellValue(sheetRecNum+"行");
			rowEnd.getCell(3).setCellStyle(formatStyle);
			rowEnd.getCell(4).setCellStyle(formatStyle);

			// 设置列宽
			ws.setColumnWidth(0, 2500);
			ws.setColumnWidth(1, 6000);
			ws.setColumnWidth(2, 5500);
			if(recNum >= list.size()) break;  
		}
		list=null;//主动清空list集合，减少内存消耗。
		String fileName = "账单明细报表" + startDate + "--" + endDate + ".xls";
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
		logger.info("【导出账单明细报表结束】beginDate=" + startDate + ",endDate="
				+ endDate);
		wb.write(response.getOutputStream());

	}

	/**
	 * 使用HSSFWorkbook导入poi-3.13-20150929.jar即可。导出格式为.xls 数据单页不能超过65535条记录，超过报错。
	 * Invalid row number (65536) outside allowable range (0..65535)
	 */
	private HSSFWorkbook BuildHSSFWorkbook(List<Trade> list) {
		HSSFWorkbook wb = new HSSFWorkbook();
		/* 报表字体 */

		Font fontColumn = wb.createFont();
		fontColumn.setFontName("宋体");
		fontColumn.setFontHeightInPoints((short) 12);// 设置字体大小
		fontColumn.setBold(true);

		// 居中样式
		CellStyle styleColumn = wb.createCellStyle();
		styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		styleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleColumn.setFont(fontColumn);

		// 两位小数点样式
		CellStyle formatStyle = wb.createCellStyle();
		formatStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
		formatStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		DataFormat format = wb.createDataFormat();
		formatStyle.setDataFormat(format.getFormat("0.00"));

		String str_name = "";

		// 报表内容
		HSSFSheet ws = wb.createSheet("交易明细报表");
		HSSFRow rowColumn0 = ws.createRow(0); // 创建行
		rowColumn0.createCell(0).setCellValue("序号");
		rowColumn0.createCell(1).setCellValue("订单号");
		rowColumn0.createCell(2).setCellValue("交易类型");

		for (int i = 0; i <= 2; i++) {
			rowColumn0.getCell(i).setCellStyle(styleColumn);
		}

		for (int i = 0; i < list.size(); i++) {
			Trade trade = list.get(i);
			System.out.println(trade.toString());
			HSSFRow rowBody = ws.createRow(i + 1);
			rowBody.createCell(0).setCellValue(i + 1);
			rowBody.createCell(1).setCellValue(trade.getOrderNo());
			rowBody.createCell(2).setCellValue(trade.getTypeMemo());

			rowBody.getCell(0).setCellStyle(styleColumn);
		}
		/*
		 * HSSFRow rowEnd = ws.createRow(list.size() + 1);
		 * rowEnd.createCell(9).setCellValue("合计");
		 * rowEnd.createCell(10).setCellValue(totalAmount);
		 * rowEnd.getCell(10).setCellStyle(formatStyle);
		 * rowEnd.createCell(11).setCellValue(totalFee);
		 * rowEnd.getCell(11).setCellStyle(formatStyle);
		 */

		// 设置列宽
		ws.setColumnWidth(0, 1500);
		ws.setColumnWidth(1, 6000);
		ws.setColumnWidth(2, 5500);
		return wb;
	}

	/**
	 * 当我们还要使用xlsx格式、还要导入poi-ooxml-version-yyyymmdd.jar，和poi-ooxml-schema.
	 * jar这个jar验证，还有xmlbeans-2.6.0.jar支持。
	 * 使用SXSSFWorkbook,可以有单页100多万条数据。修改输出的格式为xlsx。
	 */
	private SXSSFWorkbook BuildSXSSFWorkbook(List<Trade> list) {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		/* 报表字体 */

		Font fontColumn = wb.createFont();
		fontColumn.setFontName("宋体");
		fontColumn.setFontHeightInPoints((short) 12);// 设置字体大小
		fontColumn.setBold(true);

		// 居中样式
		CellStyle styleColumn = wb.createCellStyle();
		styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		styleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleColumn.setFont(fontColumn);

		// 两位小数点样式
		CellStyle formatStyle = wb.createCellStyle();
		formatStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
		formatStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		DataFormat format = wb.createDataFormat();
		formatStyle.setDataFormat(format.getFormat("0.00"));

		String str_name = "";

		// 报表内容
		SXSSFSheet ws = wb.createSheet("交易明细报表");
		SXSSFRow rowColumn0 = ws.createRow(0); // 创建行
		rowColumn0.createCell(0).setCellValue("序号");
		rowColumn0.createCell(1).setCellValue("订单号");
		rowColumn0.createCell(2).setCellValue("交易类型");

		for (int i = 0; i <= 2; i++) {
			rowColumn0.getCell(i).setCellStyle(styleColumn);
		}

		for (int i = 0; i < list.size(); i++) {
			Trade trade = list.get(i);
			System.out.println(trade.toString());
			SXSSFRow rowBody = ws.createRow(i + 1);
			rowBody.createCell(0).setCellValue(i + 1);
			rowBody.createCell(1).setCellValue(trade.getOrderNo());
			rowBody.createCell(2).setCellValue(trade.getTypeMemo());

			rowBody.getCell(0).setCellStyle(styleColumn);
		}
		/*
		 * HSSFRow rowEnd = ws.createRow(list.size() + 1);
		 * rowEnd.createCell(9).setCellValue("合计");
		 * rowEnd.createCell(10).setCellValue(totalAmount);
		 * rowEnd.getCell(10).setCellStyle(formatStyle);
		 * rowEnd.createCell(11).setCellValue(totalFee);
		 * rowEnd.getCell(11).setCellStyle(formatStyle);
		 */

		// 设置列宽
		ws.setColumnWidth(0, 1500);
		ws.setColumnWidth(1, 6000);
		ws.setColumnWidth(2, 5500);
		return wb;
	}
}
