package com.cfe.response;

import java.util.List;

import com.cfe.response.entity.OrderInfo;

//20151112-���ָ��ID��task��order�б�
public class ResponseGetFinishedTask extends AbstractResponse{
	List<OrderInfo> orders;

	public List<OrderInfo> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderInfo> orders) {
		this.orders = orders;
	}	
	
}
