package com.cfe.response.entity;

//20151108-getAllTask��������Ϣ��
public class TaskInfo {
	//{'time':ʱ��,'desc':��������,'id':����id}
	private String time;
	private String desc;
	private int id;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
