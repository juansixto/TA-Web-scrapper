package models;

public class Review {
	
	private String title;
	private String text;
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
	

}
