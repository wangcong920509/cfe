package com.cfe.common.model.dto;


public class EvluateRcrdDTO {
	private long erid;
	private CustomerDTO cid;
	private DeliverDTO did;
	private String content;
	private int level;
	private String time;
	/**
	 * @return the erid
	 */
	public long getErid() {
		return erid;
	}
	/**
	 * @param erid the erid to set
	 */
	public void setErid(long erid) {
		this.erid = erid;
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
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
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
	
}
