package agh.obiektow.proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.commons.io.IOUtils;

public class Sejm {
	ArrayList<Posel> poslowie;
	double sredniaWydatkow;
	Posel najwiecejPodrozy;
	Posel najduzejZaGranica;
	Posel najdrozszaPodroz;
	
	
	public Sejm() throws MalformedURLException, IOException, ParseException {
		try (Scanner scan = new Scanner(new BufferedReader(
				new InputStreamReader(new URL("https://api-v3.mojepanstwo.pl/dane/poslowie.json").openStream())))) {
			StringBuilder text = new StringBuilder();
			while (scan.hasNextLine()) {
				text.append(scan.nextLine());
			}
			//JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) JSONValue.parse(text.toString());
			JSONArray data = (JSONArray) json.get("Dataobject");
			//JSONArray pos = (JSONArray) data.get("data");
			System.out.println(data.get(0));
			//JSONObject geoObject = json.getJSONObject("Dataobject");
			
		}
	}
}
