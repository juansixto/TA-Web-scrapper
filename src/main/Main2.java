package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.Review;

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
		System.out.println(url);
		Document doc;
		Review fullReview = new Review();
		try {
			doc = Jsoup.connect(url).get();
			Elements reviews = doc.select(".entry");
			System.out.println(reviews.get(0).text());
			fullReview.setText(reviews.get(0).text());
			Elements rs = doc.select(".ratingDate");
			System.out.println(rs.get(0).text().substring(9, 22));
			fullReview.setDate(rs.get(0).text().substring(9, 22));
			rs = doc.select(".col2of2");
			System.out.println(rs.get(3).html());
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		return fullReview;
	}
	
	
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements reviews = null;
			Elements titles = null;
			String url = URL;
			int numberOfReviews = getNumberOfReviews(doc);
			String POIName = getPOIName(doc);
			System.out.println(POIName);
			System.out.println("Numero de Reviews: "+numberOfReviews);
			
			if(numberOfReviews != 0) {
				for(int i = 0; i < (numberOfReviews/10 + 1); i++) {
					if(i != 0) {
						url = URL.replace("Reviews-", "Reviews-or"+i*10+"-");
					}
					doc = Jsoup.connect(url).get();
					titles = doc.select(".quote > a");
					for(int j = 0; j < titles.size(); j++) {
						Review r = new Review();
						r.setTitle(titles.get(j).html());
						System.out.println(titles.get(j).toString());
						int num = titles.get(j).toString().indexOf("id=");
						String id = titles.get(j).toString().substring(num+4,num+14);
						r = getFullReview(id);
					}
				}
			}

		}
		catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}

	}

}
