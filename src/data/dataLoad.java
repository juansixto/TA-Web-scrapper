package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Review;

import org.json.JSONException;
import org.json.JSONObject;

public class dataLoad {
	private static File file;
	private static List<Review> reviewList = new ArrayList<>();
	
	public static void main(String[] args) {
		try {
			file = new File("corpus/Hotel0.json");
			FileReader fr = new FileReader (file);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			 while((linea=br.readLine())!=null){
				JSONObject obj = new JSONObject(linea);
				Review r = new Review();
				r.fromJSON(obj);
				r.print();
				reviewList.add(r);
			 }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
