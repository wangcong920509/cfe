package com.cfe.response.entity;

//20151108-getAllTask中任务信息类
public class TaskInfo {
	//{'time':时间,'desc':任务描述,'id':任务id}
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
