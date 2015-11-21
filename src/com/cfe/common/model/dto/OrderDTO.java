package com.cfe.common.model.dto;

import java.util.List;


/**
 * @author Fly2Leo
 * @version 1.0
 * @created 11-ʮ����-2013 19:54:47
 */
public class OrderDTO {

	private long oid;
	private SiteDTO sid;
	private RestaurantDTO rid;
	private CustomerDTO cid;
	private double price;
	private int state;
	private String time;
	private List<FoodDTO> food;
	private TaskDTO tid;

	public OrderDTO(){

	}
	
	public List<FoodDTO> getFood() {
		return food;
	}

	public void setFood(List<FoodDTO> food) {
		this.food = food;
	}

	/**
	 * @return the oid
	 */
	public long getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}

	/**
	 * @return the sid
	 */
	public SiteDTO getSid() {
		return sid;
	}

	/**
	 * @param sid the sid to set
	 */
	public void setSid(SiteDTO sid) {
		this.sid = sid;
	}

	/**
	 * @return the rid
	 */
	public RestaurantDTO getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(RestaurantDTO rid) {
		this.rid = rid;
	}

	/**
	 * @return the cid
	 */
	public CustomerDTO getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(CustomerDTO cid) {
		this.cid = cid;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the tid
	 */
	public TaskDTO getTid() {
		return tid;
	}

	/**
	 * @param tid the tid to set
	 */
	public void setTid(TaskDTO tid) {
		this.tid = tid;
	}



}