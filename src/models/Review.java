package models;

import org.json.JSONException;
import org.json.JSONObject;



public class Review {
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private String title;
	private String text;
	private String date;
	private float value;
	private float location;
	private float sleepQ;
	private float rooms;
	private float cleanliness;
	private float service;
	private User user;
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public float getLocation() {
		return location;
	}
	public void setLocation(float location) {
		this.location = location;
	}
	public float getSleepQ() {
		return sleepQ;
	}
	public void setSleepQ(float sleepQ) {
		this.sleepQ = sleepQ;
	}
	public float getRooms() {
		return rooms;
	}
	public void setRooms(float rooms) {
		this.rooms = rooms;
	}
	public float getCleanliness() {
		return cleanliness;
	}
	public void setCleanliness(float cleanliness) {
		this.cleanliness = cleanliness;
	}
	public float getService() {
		return service;
	}
	public void setService(float service) {
		this.service = service;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private int rating;
	
	public Review(){
		this.title = "";
		this.text = "";
		this.rating = 0;
	}
	public Review(String title, String text, int rating){
		this.title = title;
		this.text = text;
		this.rating = rating;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String toString(){
		return (this.rating + " --> " + this.title + " --> " + this.text); 
	}
	public void print(){
		System.out.println(this.rating + " --> " + this.title + " --> " + this.text);
		System.out.println("Value:" + this.value);
		System.out.println("Location:" + this.location);
		System.out.println("Sleep Quality:" + this.sleepQ);
		System.out.println("Rooms:" + this.rooms);
		System.out.println("Cleanliness:" + this.cleanliness);
		System.out.println("Service:" + this.service);
		System.out.println("==========================================================");
	}
	public 	JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("title", this.title);
			obj.put("text", this.text);
			obj.put("date", this.date);
			obj.put("value", this.value);
			obj.put("location", this.location);
			obj.put("sleepQ", this.sleepQ);
			obj.put("rooms", this.rooms);
			obj.put("cleanliness", this.cleanliness);
			obj.put("service", this.service);
			obj.put("User", this.user.getNick());
			obj.put("Reviews", this.user.getReviews());
			obj.put("Location", this.user.getLocation());
			obj.put("hotelReviews", this.user.getHotelReviews());
			obj.put("Cities", this.user.getCities());
			obj.put("Helpful", this.user.getHelpful());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}
	

}
