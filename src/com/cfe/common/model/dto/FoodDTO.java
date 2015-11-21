package com.cfe.common.model.dto;

import java.util.List;

/**
 * @author Fly2Leo
 * @version 1.0
 * @created 11-ʮ����-2013 19:54:46
 */
public class FoodDTO {

	private long fid;
	private String name;
	private double price;
	private long rid;
	private List<OFDTO> ofs_f;

	public FoodDTO(){

	}

	/**
	 * @return the fid
	 */
	public long getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(long fid) {
		this.fid = fid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the rid
	 */
	public long getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(long rid) {
		this.rid = rid;
	}

	/**
	 * @return the ofs_f
	 */
	public List<OFDTO> getOfs_f() {
		return ofs_f;
	}

	/**
	 * @param ofs_f the ofs_f to set
	 */
	public void setOfs_f(List<OFDTO> ofs_f) {
		this.ofs_f = ofs_f;
	}


}