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
	InfromacjeOgolne info=null;

	public Sejm() {
		this.poslowie = new HashMap<String, Posel>();
	}

	public void szukajPoslow() throws MalformedURLException, IOException, InterruptedException {
		JsonFromUrl dane = new JsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
		JSONObject json = dane.pobierzDane();
		int liczbaStron = ileStron(json);
		WyszukaniaNaStronie[] szukanie = new WyszukaniaNaStronie[liczbaStron + 1];
		Thread threads[] = new Thread[liczbaStron + 1];
		for (int i = 0; i <= liczbaStron; i++) {
			szukanie[i] = new WyszukaniaNaStronie(this, i);
			threads[i] = new Thread(szukanie[i]);
			threads[i].run();
		}
		for(int i=0;i<=liczbaStron ;i++){
			threads[i].join();
			if(0==i){
				this.info=szukanie[0].info;
			}else{
				this.info.modyfikuj(szukanie[i].info);
			}
		}
		System.out.println("zakonczono szukanie");
		
	}

	
	public void addPosla(Posel kolejny) {
		poslowie.put(kolejny.imieNazwisko, kolejny);
	}

	private int ileStron(JSONObject json) throws IOException {
		JSONObject links = (JSONObject) json.get("Links");
		String lastPage = (String) links.get("last");
		Pattern liczbaNaKoncu = Pattern.compile("(\\d+)$");
		Matcher dopasowane = liczbaNaKoncu.matcher(lastPage);
		if (!dopasowane.find())
			throw new IOException();
		return Integer.valueOf(lastPage.substring(dopasowane.start()));
	}

}
