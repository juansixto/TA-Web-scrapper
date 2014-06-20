package main;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CityScrapper {
	private static String URL = "http://www.tripadvisor.co.uk/Hotels-g186403-oa0-Coventry_West_Midlands_England-Hotels.html#ACCOM_OVERVIEW";
	private static List<String> hotelList = new ArrayList<>();
	
	
	public static int getNumberOfReviews(Document doc) {

		String StrNumberOfReviews = doc.select(".pgCount").get(0).text();
		StrNumberOfReviews= (StrNumberOfReviews.substring(0,StrNumberOfReviews.indexOf(" of ")));
		int numberOfReviews = Integer.parseInt(StrNumberOfReviews);
		return numberOfReviews;
	}
	
	public static void main(String[] args) {
		try {
			String url = URL;	
			Document doc = Jsoup.connect(url).get();
			int numberOfReviews = getNumberOfReviews(doc);
			if(numberOfReviews != 0) {
				for(int k = 0; k < (numberOfReviews/10 + 1); k++) {
					if(k != 0) {
						url = URL.replace("-oa0-", "-oa"+k*10+"-");
					}	
					doc = Jsoup.connect(url).get();
					Elements hotels = doc.select(".quality");
					for(int i=0; i < hotels.size(); i++){
						String hotel = hotels.get(i).html().substring(25,hotels.get(i).html().indexOf("id=")-3);
						hotelList.add(hotel);
					}
					for(int j=0; j < hotels.size(); j++){
						System.out.println("Hotel "+ ((k*10)+j) + " de " +numberOfReviews);
						System.out.println("http://www.tripadvisor.co.uk"+hotelList.get(j));
						HotelScrapper hs = new HotelScrapper("http://www.tripadvisor.co.uk"+hotelList.get(j), "Hotel"+j);
						hs.extract();
					}
				}
			}
		}
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

}

