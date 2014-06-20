package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.Review;
import models.User;

public class Main2 {

	private static String URL = "http://www.tripadvisor.co.uk/Hotel_Review-g503819-d571157-Reviews-Premier_Inn_Manchester_Salford_Quays-Salford_Greater_Manchester_England.html";
	private static String URL2 = "http://www.tripadvisor.co.uk/ShowUserReviews-g503819-d571157-Reviews-Premier_Inn_Manchester_Salford_Quays-Salford_Greater_Manchester_England.html#CHECK_RATES_CONT";
	private static List<Review> reviewList = new ArrayList<>();
	
	public static int getNumberOfReviews(Document doc) {

		String StrNumberOfReviews = doc.select(".reviews_header").text();
		StrNumberOfReviews = StrNumberOfReviews.replaceAll("\\D+","");
		int numberOfReviews = Integer.parseInt(StrNumberOfReviews);
		return numberOfReviews;
	}
	
	public static String getPOIName(Document doc) {
		String heading = doc.select("#HEADING").text();
		return heading;
	}
	
	public static Review getFullReview(String code) {
		String url = URL2.replace("-Reviews-", "-"+code+"-");
		Document doc;
		Review fullReview = new Review();
		try {
			doc = Jsoup.connect(url).get();
			Elements reviews = doc.select(".entry");
			fullReview.setText(reviews.get(0).text());
			Elements rs = doc.select(".ratingDate");
			fullReview.setDate(rs.get(0).text().substring(9, rs.get(0).text().length()));
			rs = doc.select(".col2of2");
			fullReview.setValue(getRating(rs.get(3),"</span> Value</li>"));
			fullReview.setLocation(getRating(rs.get(3),"</span> Location</li> "));
			fullReview.setSleepQ(getRating(rs.get(3),"</span> Sleep Quality</li> "));
			fullReview.setRooms(getRating(rs.get(3),"</span> Rooms</li> "));
			fullReview.setCleanliness(getRating(rs.get(3),"</span> Cleanliness</li> "));
			fullReview.setService(getRating(rs.get(3),"</span> Service</li> "));
			User user = new User();
			user = getUser(doc);
			fullReview.setUser(user);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		return fullReview;
	}
	
	
	private static User getUser(Document doc) {
		User user = new User();
		Elements users = doc.select(".username");
		user.setNick(users.get(0).text());
		users = doc.select(".location");
		user.setLocation(users.get(0).text());
		users = doc.select(".col1of2");
		Element contributor = users.get(1);
		Elements attribs = contributor.select(".badgeText");
			for(int i = 0; i < attribs.size(); i++){
					String text = attribs.get(i).text();
					if(text.indexOf("hotel")>-1){
						user.setHotelReviews(Integer.parseInt(text.substring(0, text.indexOf("hotel")-1)));
					}
					else if(text.indexOf("review")>-1){
						user.setReviews(Integer.parseInt(text.substring(0, attribs.get(0).text().indexOf("rev")-1)));
					}
					else if(text.indexOf("cities")>-1){
						user.setCities(Integer.parseInt(text.substring(text.indexOf("in")+3, text.indexOf("cities")-1)));
					}
					else if(text.indexOf("helpful")>-1){
						user.setHelpful(Integer.parseInt(text.substring(0, text.indexOf("helpful")-1)));
					}
			}		

		return null;
	}

	private static float getRating(Element element, String string) {
		int index = element.html().indexOf(string);
		if(index > -1){
			return Float.parseFloat(element.html().substring(index-8, index-5));
		} else {
			return 0;
		}

	}

	public static void main(String[] args) {
		try {
			System.out.println("HAAA");
			Document doc = Jsoup.connect(URL).get();
			System.out.println(doc);
			Elements titles = null;
			Elements ratings = null;
			String url = URL;
			int numberOfReviews = getNumberOfReviews(doc);
			String POIName = getPOIName(doc);
			System.out.println(POIName);
			System.out.println("Numero de Reviews: "+numberOfReviews);
			FileWriter file = new FileWriter("corpus.json");
						
			if(numberOfReviews != 0) {
				for(int i = 0; i < (numberOfReviews/10 + 1); i++) {
					if(i != 0) {
						url = URL.replace("Reviews-", "Reviews-or"+i*10+"-");
					}
					doc = Jsoup.connect(url).get();
					titles = doc.select(".quote > a");
					ratings = doc.select(".reviewItemInLine");
					for(int j = 0; j < titles.size(); j++) {
						System.out.println("Review " + (reviewList.size()+1));
						Review r = new Review();		
						int num = titles.get(j).toString().indexOf("id=");
						String id = titles.get(j).toString().substring(num+4,num+14);
						r = getFullReview(id);
						r.setTitle(titles.get(j).html());
						int reviewRate = Integer.parseInt(ratings.get(j).html().substring(ratings.get(j).html().indexOf("content")+9, ratings.get(j).html().indexOf("content")+10));
						r.setRating(reviewRate);
						r.print();
						reviewList.add(r);
						file.write(r.toJSON().toString() + ","+"\n");
					}
				}
			}
			file.flush();
			file.close();
		}
		catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}


	}

}
