package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	private static String URL = "http://www.tripadvisor.co.uk/Hotel_Review-g503819-d571157-Reviews-Premier_Inn_Manchester_Salford_Quays-Salford_Greater_Manchester_England.html";
	private static String URL2 = "http://www.tripadvisor.co.uk/ShowUserReviews-g503819-d571157-Reviews-Premier_Inn_Manchester_Salford_Quays-Salford_Greater_Manchester_England.html#CHECK_RATES_CONT";
	private static List<String> reviewList = new ArrayList<>();

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
	
	public static Element getFullReview(String code) {
		
		String url = URL2.replace("-Reviews-", "-"+code+"-");
		Document doc;
		Element fullReview = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements reviews = doc.select(".entry");
			fullReview = reviews.get(0);
			
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
			Elements ratings = null;
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
					reviews = doc.select(".partial_entry");
					titles = doc.select(".quote > a");
					ratings = doc.select(".reviewItemInLine");
					System.out.println(ratings.size());
					int titlesNum = 0;
					for(int j = 0; j < reviews.size(); j++) {
						

						String review = reviews.get(j).text();
						if(!reviews.get(j).html().contains("<span id=\"respons"))
						{
							review = review.replaceAll("\"", "");
							if(!review.endsWith(".")) {
								review += ".";
							}
							String html = reviews.get(j).html();
							Element fullReview = reviews.get(j);
							int num = html.indexOf("review_");
							if (num > -1){
								String code = "r" + html.substring(num+7, num+16);
								fullReview = getFullReview(code);
							}
								String tittleTemp = titles.get(titlesNum++).text();
								reviewList.add(tittleTemp + " --> " + fullReview.text());
								System.out.println(ratings.get(j).text() + " -- " + tittleTemp + " --> " + fullReview.text());
						}	
					}
					
					
				}
			}
			System.out.println("End");
		}
		catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
	}

}
