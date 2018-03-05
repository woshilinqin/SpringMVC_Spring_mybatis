package com.tgb.model;

import java.io.Serializable;

public class Trade implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String orderNo;
	private String typeMemo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTypeMemo() {
		return typeMemo;
	}
	public void setTypeMemo(String typeMemo) {
		this.typeMemo = typeMemo;
	}
	@Override
	public String toString() {
		return "Trade [id=" + id + ", orderNo=" + orderNo + ", typeMemo="
				+ typeMemo + "]";
	}
	
	

}
