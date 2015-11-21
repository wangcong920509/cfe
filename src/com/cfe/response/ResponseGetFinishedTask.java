package com.cfe.response;

import java.util.List;

import com.cfe.response.entity.OrderInfo;

//20151112-获得指定ID的task中order列表
public class ResponseGetFinishedTask extends AbstractResponse{
	List<OrderInfo> orders;

	public List<OrderInfo> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderInfo> orders) {
		this.orders = orders;
	}	
	
}
