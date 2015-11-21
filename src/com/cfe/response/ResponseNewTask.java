package com.cfe.response;

import java.util.List;

import com.cfe.response.entity.PositionInfo;

public class ResponseNewTask extends AbstractResponse{
	List<PositionInfo> on_task;

	public List<PositionInfo> getOn_task() {
		return on_task;
	}

	public void setOn_task(List<PositionInfo> on_task) {
		this.on_task = on_task;
	}	
}
