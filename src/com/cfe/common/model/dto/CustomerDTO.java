package com.cfe.common.model.dto;

import java.util.List;

/**
 * @author Fly2Leo
 * @version 1.0
 * @created 11-ʮ����-2013 19:54:43
 */
public class CustomerDTO {

	private long cid;
	private String name;
	private String address;
	private String phone;
	private String job;
	private String mail;
	private PositionDTO pid;
	private List<EvluateRcrdDTO> ercrd;
//	private Set<EvluateRcrd> ercrd;

	public CustomerDTO(){

	}

	/**
	 * @return the cid
	 */
	public long getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(long cid) {
		this.cid = cid;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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