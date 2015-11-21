package com.cfe.common.model.dto;

import java.util.List;

public class DeliverDTO {
	private long did;
	private String name;
	private String sex;
	private int age;
	private String phone;
	private int isWork;
	private PositionDTO pid;
	private List<EvluateRcrdDTO> ercrd;
	/**
	 * @return the did
	 */
	public long getDid() {
		return did;
	}
	/**
	 * @param did the did to set
	 */
	public void setDid(long did) {
		this.did = did;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the isWork
	 */
	public int getIsWork() {
		return isWork;
	}
	/**
	 * @param isWork the isWork to set
	 */
	public void setIsWork(int isWork) {
		this.isWork = isWork;
	}
	/**
	 * @return the pid
	 */
	public PositionDTO getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(PositionDTO pid) {
		this.pid = pid;
	}
	/**
	 * @return the ercrd
	 */
	public List<EvluateRcrdDTO> getErcrd() {
		return ercrd;
	}
	/**
	 * @param ercrd the ercrd to set
	 */
	public void setErcrd(List<EvluateRcrdDTO> ercrd) {
		this.ercrd = ercrd;
	}
}
