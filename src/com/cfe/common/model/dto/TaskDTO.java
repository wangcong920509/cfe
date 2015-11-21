package com.cfe.common.model.dto;

import java.util.List;

/**
 * @author Fly2Leo
 * @version 1.0
 * @created 11-ʮ����-2013 19:54:50
 */
public class TaskDTO {

	private long tkid;
	private int state;
	private double distance;
	private String time;
	private String path;
	private List<OrderDTO> orders;
	private DeliverDTO did;

	public TaskDTO(){

	}

	/**
	 * @return the tkid
	 */
	public long getTkid() {
		return tkid;
	}

	/**
	 * @param tkid the tkid to set
	 */
	public void setTkid(long tkid) {
		this.tkid = tkid;
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
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the orders
	 */
	public List<OrderDTO> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	/**
	 * @return the did
	 */
	public DeliverDTO getDid() {
		return did;
	}

	/**
	 * @param did the did to set
	 */
	public void setDid(DeliverDTO did) {
		this.did = did;
	}
}