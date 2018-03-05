package com.tgb.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgb.mapper.TradeMapper;
import com.tgb.model.Trade;
import com.tgb.service.ITradeService;
@Service
@Transactional
public class TradeServiceImpl implements ITradeService{
	@Resource
	private TradeMapper tradeMapper;
	public List<Trade> findAll(Map<String, Object> map) {
		return tradeMapper.findAll(map);
	}
	public List<Trade> findByLimit(Map<String, Object> map) {
		return tradeMapper.findByLimit(map);
	}
	public int selectCount(Map<String, Object> map) {
		return tradeMapper.selectCount(map);
	}

}
