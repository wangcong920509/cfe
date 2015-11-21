package com.cfe.http.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

import com.bjtu.cfe.R.string;
import com.cfe.common.model.dto.OrderDTO;
import com.cfe.common.model.dto.TaskDTO;
import com.cfe.response.ResponseFinishCurrent;
import com.cfe.response.ResponseGetAllTasks;
import com.cfe.response.ResponseGetFinishedTask;
import com.cfe.response.ResponseNewTask;
import com.cfe.response.entity.TaskInfo;

public class DataTransfer {
	
	private static String encode = "utf-8";
	private static final String path1 = "http://192.168.23.10:8080/CallFindEat/request/mobileTask";
	private static final String path2 = "http://192.168.23.10:8080/CallFindEat/request/mobileOrder";

	private static final String pathCom = "http://166.111.81.196:8078/";
	private DataTransfer() {
		
	}
	
	//20151105-登录
	public static boolean login(String phone, String pass){
		String login_path = "login/";		
		String url = pathCom + login_path;
		String params = "?phone="+phone+"&pass="+pass;
		url += params;
		
		boolean isLogin = false;
		String result;
		try {
			result = HttpUtils.doGet(url,encode);
			//验证处理result结果
			JSONObject jsonObject = new JSONObject(result);
			int state = jsonObject.getInt("state");
			//0：登录失败；1：登陆成功；2：参数缺失
			switch(state){
			case 0:
				//return false;
				isLogin = false;
			case 2:
				break;
			case 1:
				isLogin = true;
				break;
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 	isLogin;	
	}

	//20151112-结束当前任务
	//http://166.111.81.196:8078/finish_current/
	public static ResponseFinishCurrent finishCurrent(String phone){
		String path = "finish_current/";
		String param = "?phone="+phone;
		String url = pathCom + path + param;
		
		String result;
		ResponseFinishCurrent response = new ResponseFinishCurrent();
		try {
			result = HttpUtils.doGet(url, encode);
			ObjectMapper mapper = new ObjectMapper();			
			response = mapper.readValue(result, ResponseFinishCurrent.class);	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return response;
	}
	
	//2015112-获得指定ID的task中order列表
	//http://166.111.81.196:8078/finished_task/
	public static ResponseGetFinishedTask getFinishedTask(int taskid){
		String path = "finished_task/";
		String param = "?taskid="+taskid;
		String url = pathCom + path + param;
		
		String result;
		ResponseGetFinishedTask response = new ResponseGetFinishedTask();
		try {
			result = HttpUtils.doGet(url, encode);
			ObjectMapper mapper = new ObjectMapper();			
			response = mapper.readValue(result, ResponseGetFinishedTask.class);	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return response;
	}
	
	//20151108-获取所有任务
	/**
	 * 
	 * @param phone 送餐员手机号
	 * @param x 经度
	 * @param y 纬度
	 * @return 任务列表
	 */
	public static ResponseGetAllTasks getAllTasks(String phone){		
		List<TaskDTO> taskList = new ArrayList<TaskDTO>();
		String path = "all_task/";
		String param = "?phone="+phone;
		String url = pathCom + path + param;
		
		String result;
		ResponseGetAllTasks response = new ResponseGetAllTasks();
		try {
			result = HttpUtils.doGet(url,encode);
			//JSONObject jsonObject = new JSONObject(result);
			
			ObjectMapper mapper = new ObjectMapper();
			//response = mapper.readValue(tmp1, ResponseGetAllTasks.class);
			//List<TaskInfo> taskInfos = (List<TaskInfo>) mapper.readValue(tmp, TaskInfo.class);
			//result = "{\"state\": 1, \"finished\":[{\"time\":\"2015-11-07\",\"desc\":\"babba\",\"id\":12}], \"on_task\":[{\"x\":41.2,\"y\":41.2,\"desc\":\"lalla\"]}}";
			response = mapper.readValue(result, ResponseGetAllTasks.class);
			//JSONObject jsonObject = new JSONObject(result);
			//taskList = JsonTools.jsonToTasks(jsonObject);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return response;
	}
	

	//20151121
	//http://166.111.81.196:8078/new_task/﻿
	public static ResponseNewTask getNewTask(String phone){
		String path = "new_task/";
		String param = "?phone="+phone;
		String url = pathCom + path + param;
		
		String result;
		ResponseNewTask response = new ResponseNewTask();
		try {
			result = HttpUtils.doGet(url, encode);
			ObjectMapper mapper = new ObjectMapper();			
			response = mapper.readValue(result, ResponseNewTask.class);	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return response;		
	}
	
	public static List<TaskDTO> getTask(String id) {
		List<TaskDTO> tasks = null;
		Map<String, String> param = new HashMap<String, String>();
		String result = "";
		
		param.put("id", id);
		try {
			result = HttpUtils.sendHttpClientPost(path1, param, encode);
			JSONObject jsonObject = new JSONObject(result);
			tasks = JsonTools.getTasks(jsonObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tasks;
	}
	
	public static List<OrderDTO> getOrderDTO(String tkid) {
		List<OrderDTO> orders = null;
		
		Map<String, String> param = new HashMap<String, String>();
		String result = "";
		
		param.put("id", tkid);
		try {
			result = HttpUtils.sendHttpClientPost(path2, param, encode);
			JSONObject jsonObject = new JSONObject(result);
			orders = JsonTools.getOrders(jsonObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orders;
	}

}
