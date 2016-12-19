package agh.obiektow.proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.*;

public class Sejm {
	HashMap<Integer,Posel> poslowie;
	double sredniaWydatkow;

	public Sejm() throws MalformedURLException, IOException {
		try (Scanner scan = new Scanner(new BufferedReader(
				new InputStreamReader(new URL("https://api-v3.mojepanstwo.pl/dane/poslowie.json").openStream())))) {

			StringBuilder text = new StringBuilder();
			while (scan.hasNextLine()) {
				text.append(scan.nextLine());
			}
			System.out.println(text.toString());
			JSONObject json = (JSONObject) JSONValue.parse(text.toString());

			// JSONArray data = (JSONArray) json.get("Dataobject");
			// JSONArray pos = (JSONArray) data.get("data");
			// System.out.println(data.get(0));
			// JSONObject geoObject = json.getJSONObject("Dataobject");

		}
	}

	private InfromacjeOgolne getInfo(int nrStrony) throws MalformedURLException, IOException {
		try (Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new URL(
				"https://api-v3.mojepanstwo.pl/dane/poslowie.json?_type=objects&page=" + String.valueOf(nrStrony))
						.openStream())))) {
			StringBuilder text = new StringBuilder();
			while (scan.hasNextLine()) {
				text.append(scan.nextLine());
			}
			//System.out.println(text.toString());
			JSONObject json = (JSONObject) JSONValue.parse(text.toString());
			JSONArray dataobject = (JSONArray) json.get("Dataobject");
		}
	}
}
