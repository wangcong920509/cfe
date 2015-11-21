package com.cfe.common.model.dto;

/**
 * @author Fly2Leo
 * @version 1.0
 * @created 11-ʮ����-2013 19:54:45
 */
public class EvaluateDTO {

	private long cid;
	private long did;
	private long id;
	private int level;

	public EvaluateDTO(){

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

	
}