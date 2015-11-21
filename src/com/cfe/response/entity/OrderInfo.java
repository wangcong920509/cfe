package com.cfe.response.entity;

public class OrderInfo {
	
	private String id;//¶©µ¥±àºÅ,
	private String price;//¶©µ¥½ð¶î,
	private RestaurantInfo restaurant;
	private CustomerInfo customer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public RestaurantInfo getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(RestaurantInfo restaurant) {
		this.restaurant = restaurant;
	}
	public CustomerInfo getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerInfo customer) {
		this.customer = customer;
	}
}
