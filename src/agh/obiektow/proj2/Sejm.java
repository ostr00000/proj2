package agh.obiektow.proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.*;

public class Sejm {
	HashMap<String, Posel> poslowie;
	double sredniaWydatkow;
	
	public Sejm()  {
		
		//JSONArray dataobject = (JSONArray) json.get("Dataobject");	
		
		
	}
	public void szukajPoslow()throws MalformedURLException, IOException{
		JSONObject json = pobierzDane("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
		int liczbaStron = ileStron(json);
		WyszukaniaNaStronie [] szukanie = new WyszukaniaNaStronie [liczbaStron+1];
		for(int i=0;i<=liczbaStron;i++){
			szukanie[i] = new WyszukaniaNaStronie(this,i);
			szukanie[i].run();
		}

		
	}
	static public JSONObject pobierzDane(String url) {
		try (Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new URL(url).openStream())))) {
			StringBuilder text = new StringBuilder();
			while (scan.hasNextLine()) {
				text.append(scan.nextLine());
			}
			JSONObject ret = (JSONObject) JSONValue.parse(text.toString());
			return ret;
		} catch (MalformedURLException e) {
			System.out.println("problem z url");
		} catch (IOException e) {
			System.out.println("problem z odczytem");
		}
		return null;// poprawic?
	}
	public void addPosla(Posel kolejny){
		poslowie.put(kolejny.imieNazwisko, kolejny);
	}

	private int ileStron(JSONObject json)throws IOException {
		JSONObject links = (JSONObject) json.get("Links");
		String lastPage = (String) links.get("last");
		Pattern liczbaNaKoncu = Pattern.compile("(\\d+)$");
		Matcher dopasowane = liczbaNaKoncu.matcher(lastPage);
		if (!dopasowane.find())
			throw new IOException();
		return Integer.valueOf(lastPage.substring(dopasowane.start()));
	}
	
}
