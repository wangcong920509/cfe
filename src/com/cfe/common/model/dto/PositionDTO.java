package com.cfe.common.model.dto;

/**
 * @author Fly2Leo
 * @version 1.0
 * @created 11-ʮ����-2013 19:54:48
 */
public class PositionDTO {

	private long id;
	private double px;
	private double py;
	private String refer;
	private int type;

	public PositionDTO(){

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the px
	 */
	public double getPx() {
		return px;
	}

	/**
	 * @param px the px to set
	 */
	public void setPx(double px) {
		this.px = px;
	}

	/**
	 * @return the py
	 */
	public double getPy() {
		return py;
	}

	/**
	 * @param py the py to set
	 */
	public void setPy(double py) {
		this.py = py;
	}

	/**
	 * @return the refer
	 */
	public String getRefer() {
		return refer;
	}

	/**
	 * @param refer the refer to set
	 */
	public void setRefer(String refer) {
		this.refer = refer;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}