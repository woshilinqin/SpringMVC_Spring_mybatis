package com.tgb.mapper;

import java.util.List;
import java.util.Map;

import com.tgb.model.Trade;

public interface TradeMapper {
	public List<Trade> findAll(Map<String, Object> map);
	public List<Trade> findByLimit(Map<String,Object> map);
	public int selectCount(Map<String,Object> map);
}
