package com.tgb.service;

import java.util.List;
import java.util.Map;

import com.tgb.model.Trade;

public interface ITradeService {
	List<Trade> findAll(Map<String, Object> map);
	public List<Trade> findByLimit(Map<String,Object> map);
	public int selectCount(Map<String,Object> map);
}
