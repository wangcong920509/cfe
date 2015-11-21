package com.cfe.response;

import java.util.List;

import org.apache.http.client.methods.AbortableHttpRequest;
import org.codehaus.jackson.annotate.JsonIgnoreType;

import com.cfe.response.entity.PositionInfo;
import com.cfe.response.entity.TaskInfo;

@JsonIgnoreType
//2015-11-09-返回所有任务
public class ResponseGetAllTasks extends AbstractResponse{

	List<PositionInfo> on_task;//待完成的任务
	List<TaskInfo> finished;//已经完成的任务
	
	public List<PositionInfo> getOn_task() {
		return on_task;
	}
	public void setOn_task(List<PositionInfo> on_task) {
		this.on_task = on_task;
	}
	public List<TaskInfo> getFinished() {
		return finished;
	}
	public void setFinished(List<TaskInfo> finished) {
		this.finished = finished;
	}
	

	
}
