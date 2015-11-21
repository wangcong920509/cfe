package com.cfe.http.util;

import com.cfe.response.*;
import com.cfe.response.entity.PositionInfo;
import com.cfe.response.entity.TaskInfo;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cfe.common.model.dto.CustomerDTO;
import com.cfe.common.model.dto.FoodDTO;
import com.cfe.common.model.dto.OrderDTO;
import com.cfe.common.model.dto.PositionDTO;
import com.cfe.common.model.dto.RestaurantDTO;
import com.cfe.common.model.dto.TaskDTO;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonTools {

	private JsonTools() {

	}

	public static CustomerDTO getCustomer(JSONObject jsonObject)
			throws JSONException {
		CustomerDTO customer = new CustomerDTO();

		try {
			customer.setCid(jsonObject.getLong("cid"));
			customer.setName(URLDecoder.decode(jsonObject.getString("name"), "UTF-8"));
			customer.setAddress(URLDecoder.decode(jsonObject.getString("address"), "UTF-8"));
			customer.setPhone(jsonObject.getString("phone"));
			customer.setJob(URLDecoder.decode(jsonObject.getString("job"), "UTF-8"));
			customer.setMail(URLDecoder.decode(jsonObject.getString("mail"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonString = jsonObject.getString("pid");
		JSONObject jsonObject2 = new JSONObject(jsonString);
		customer.setPid(getPosition(jsonObject2));
		// customer.put("ercrd",getEvluateRcrds(jsonObject.getJSONObject("ercrd")));

		return customer;
	}

//	public static Map<String, Object> getDeliver(JSONObject jsonObject)
//			throws JSONException {
//		Map<String, Object> deliver = new HashMap<String, Object>();
//
//		deliver.put("did", jsonObject.getLong("did") + "");
//		deliver.put("name", jsonObject.getString("name"));
//		deliver.put("sex", jsonObject.getString("sex"));
//		deliver.put("age", jsonObject.getInt("age") + "");
//		deliver.put("phone", jsonObject.getString("phone"));
//		deliver.put("isWork", jsonObject.getInt("isWork") + "");
//
//		String jsonString = jsonObject.getString("pid");
//		JSONObject jsonObject2 = new JSONObject(jsonString);
//		deliver.put("pid", getPosition(jsonObject2));
//		// deliver.put("ercrd",getEvluateRcrds(jsonObject.getJSONObject("ercrd")));
//
//		return deliver;
//	}

//	public static Map<String, Object> getEvaluate(JSONObject jsonObject)
//			throws JSONException {
//		Map<String, Object> evaluate = new HashMap<String, Object>();
//
//		evaluate.put("cid", jsonObject.getLong("cid") + "");
//		evaluate.put("did", jsonObject.getLong("did") + "");
//		evaluate.put("id", jsonObject.getLong("id") + "");
//		evaluate.put("level", jsonObject.getInt("level") + "");
//
//		return evaluate;
//	}

//	public static Map<String, Object> getEvaluateRcrd(JSONObject jsonObject)
//			throws JSONException {
//		Map<String, Object> evluateRcrd = new HashMap<String, Object>();
//
//		evluateRcrd.put("erid", jsonObject.getLong("erid") + "");
//
//		String jsonString = jsonObject.getString("cid");
//		JSONObject jsonObject2 = new JSONObject(jsonString);
//		evluateRcrd.put("cid", getCustomer(jsonObject2));
//
//		String jsonString2 = jsonObject.getString("did");
//		JSONObject jsonObject3 = new JSONObject(jsonString2);
//		evluateRcrd.put("did", getDeliver(jsonObject3));
//
//		evluateRcrd.put("content", jsonObject.getString("content"));
//		evluateRcrd.put("level", jsonObject.getInt("level") + "");
//		evluateRcrd.put("time", jsonObject.getString("time"));
//
//		return evluateRcrd;
//	}

	public static FoodDTO getFood(JSONObject jsonObject) throws JSONException {
		FoodDTO food = new FoodDTO();

		try {
		food.setFid(jsonObject.getLong("fid"));
		food.setName(URLDecoder.decode(jsonObject.getString("name"), "UTF-8"));
		food.setPrice(jsonObject.getDouble("price"));
		food.setRid(jsonObject.getLong("rid"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// food.put("ofs_f",getOfs(jsonObject.getJSONObject("ofs_f")));

		return food;
	}

	public static OrderDTO getOrder(JSONObject jsonObject) throws JSONException {
		OrderDTO order = new OrderDTO();
		
		order.setOid(jsonObject.getLong("oid"));
		
		String jsonString2 = jsonObject.getString("rid");
		JSONObject jsonObject3 = new JSONObject(jsonString2);
		order.setRid(getRestaurant(jsonObject3));
		
		String jsonString3 = jsonObject.getString("cid");
		JSONObject jsonObject4 = new JSONObject(jsonString3);
		order.setCid(getCustomer(jsonObject4));
		
//		String jsonString4 = jsonObject.getString("ofs_o");
//		JSONObject jsonObject5 = new JSONObject(jsonString4);
//		order.setFood(getFoods(jsonObject5));
		
		order.setPrice(jsonObject.getDouble("price"));
		order.setState(jsonObject.getInt("state"));
		order.setTime(jsonObject.getString("time"));
		
		return order;
	}

	public static PositionDTO getPosition(JSONObject jsonObject)
			throws JSONException {
		PositionDTO position = new PositionDTO();

		position.setId(jsonObject.getLong("pid"));
		position.setPx(jsonObject.getDouble("x"));
		position.setPy(jsonObject.getDouble("y"));
		position.setRefer(jsonObject.getString("refer"));
		position.setType(jsonObject.getInt("type"));

		return position;
	}

	public static RestaurantDTO getRestaurant(JSONObject jsonObject)
			throws JSONException {
		RestaurantDTO restaurant = new RestaurantDTO();

		try {
		restaurant.setAddress(URLDecoder.decode(jsonObject.getString("address"), "UTF-8"));
		restaurant.setInfo(URLDecoder.decode(jsonObject.getString("info"), "UTF-8"));
		restaurant.setMail(jsonObject.getString("mail"));
		restaurant.setName(URLDecoder.decode(jsonObject.getString("name"), "UTF-8"));
		restaurant.setPassword(jsonObject.getString("password"));
		restaurant.setPhone(jsonObject.getString("phone"));

		String jsonString4 = jsonObject.getString("pid");
		JSONObject jsonObject5 = new JSONObject(jsonString4);
		restaurant.setPid(getPosition(jsonObject5));

		restaurant.setRid(jsonObject.getLong("rid"));
		restaurant.setUsername(URLDecoder.decode(jsonObject.getString("username"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return restaurant;
	}

	public static TaskDTO getTask(JSONObject jsonObject) throws JSONException {
		TaskDTO task = new TaskDTO();

		task.setDistance(jsonObject.getDouble("distance"));
		task.setPath(jsonObject.getString("path"));
		task.setTime(jsonObject.getString("time"));
		task.setTkid(jsonObject.getLong("tkid"));

		return task;
	}

	public static List<OrderDTO> getOrders(JSONObject jsonObject)
			throws JSONException {
		List<OrderDTO> orders = new ArrayList<OrderDTO>();

		JSONArray jsonArray = jsonObject.getJSONArray("orders");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			orders.add(getOrder(jsonObject));
		}

		return orders;
	}

	public static List<FoodDTO> getOfs(JSONObject jsonObject)
			throws JSONException {
		List<FoodDTO> ofs = new ArrayList<FoodDTO>();

		JSONArray jsonArray = jsonObject.getJSONArray("");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			ofs.add(getFood(jsonObject));
		}

		return ofs;
	}
	
//	//20151108-json×ª»»³ÉResponseGetAllTasks
//	public static ResponseGetAllTasks jsonToResponseGetAllTasks(String body) throws JSONException{
//
////		ResponseGetAllTasks response = new ResponseGetAllTasks();
////		List<PositionInfo> positions = new ArrayList<PositionInfo>();
////		List<TaskInfo> tasks = new ArrayList<TaskInfo>();
//		
//		ObjectMapper mapper = new ObjectMapper();
//		ResponseGetAllTasks response = mapper.readValue(body, ResponseGetAllTasks.class);
//		
////		JSONArray jsonOnTaskArray = jsonObject.getJSONArray("on-task");
////		JSONArray jsonFinishedArray = jsonObject.getJSONArray("finished");
////		for(int i=0; i<jsonOnTaskArray.length();i++){
////			jsonObject = jsonOnTaskArray.get(i);
////			positions.add(jsonObject);
////		}
////		
//		return response;
//	}

	public static List<TaskDTO> getTasks(JSONObject jsonObject)
			throws JSONException {
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();

		JSONArray jsonArray = jsonObject.getJSONArray("tasks");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			tasks.add(getTask(jsonObject));
		}

		return tasks;
	}
	
	public static List<FoodDTO> getFoods(JSONObject jsonObject) throws JSONException {
		List<FoodDTO> foods = new ArrayList<FoodDTO>();

		JSONArray jsonArray = jsonObject.getJSONArray("tasks");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			foods.add(getFood(jsonObject));
		}

		return foods;
	}

}
