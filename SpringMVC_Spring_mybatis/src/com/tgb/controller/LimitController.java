package com.tgb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tgb.model.Trade;
import com.tgb.service.ITradeService;
import com.tgb.util.HCPagination;
/**
 * 研究分页，类
 * @author 林钦
 *
 */
@Controller
public class LimitController {
	@Resource
	private ITradeService tradeService;
	@RequestMapping("/getTradeList")
	public String getTradeList(HttpServletRequest request,HCPagination pagination){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("from", pagination.getStartIndex());
		map.put("all", pagination.getPageSize());
		List<Trade> list = tradeService.findByLimit(map);
		int count = tradeService.selectCount(map);
		pagination.setAllRows(count);
		request.setAttribute("list", list);
		 request.setAttribute("pagination", pagination);
		return "jsp/TradeList";
	}

}
