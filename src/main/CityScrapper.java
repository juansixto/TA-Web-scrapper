package main;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CityScrapper {

	private static String URL = "http://www.tripadvisor.co.uk/Hotels-g187052-Greater_Manchester_England-Hotels.html";
	private static List<String> hotelList = new ArrayList<>();
	
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements hotels = doc.select(".quality");
			for(int i=0; i < hotels.size(); i++){
				String hotel = hotels.get(i).html().substring(9,hotels.get(i).html().indexOf("class=")-2);
				hotelList.add(hotel);
			}
			for(int j=0; j < hotels.size(); j++){
				System.out.println("Hotel "+ j + " de " +hotels.size());
				HotelScrapper hs = new HotelScrapper("http://www.tripadvisor.co.uk"+hotelList.get(j), "Hotel"+j);
				hs.extract();
			}
		}
		catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}

	}

}
