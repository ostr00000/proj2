package agh.obiektow.proj2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.*;

public class Sejm {
	Argumenty arg;
	HashMap<String, Posel> poslowie;
	InfromacjeOgolne info = null;

	public Sejm(Argumenty arg) {
		this.arg = arg;
		this.poslowie = new HashMap<String, Posel>();
	}

	public void szukajPoslow() throws MalformedURLException, IOException, InterruptedException {
		JsonFromUrl dane = new JsonFromUrl(
				"https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=" + arg.kadencja);
		JSONObject json = dane.pobierzDane();
		int liczbaStron = ileStron(json);
		WyszukaniaNaStronie[] szukanie = new WyszukaniaNaStronie[liczbaStron + 1];
		Thread threads[] = new Thread[liczbaStron + 1];
		for (int i = 0; i <= liczbaStron; i++) {
			if (czyPrzerwac(0,i,threads)){
				return;
			}
			szukanie[i] = new WyszukaniaNaStronie(this, i);
			threads[i] = new Thread(szukanie[i]);
			threads[i].start();
		}
		for (int i = 0; i <= liczbaStron; i++) {
			if(czyPrzerwac(0,liczbaStron+1,threads))
				return;
			threads[i].join();
			if (0 == i) {
				this.info = szukanie[0].info;
			} else {
				this.info.modyfikuj(szukanie[i].info);
			}
		}
	}

	public void addPosla(Posel kolejny) {
		this.poslowie.put(kolejny.imieNazwisko, kolejny);
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
	private boolean czyPrzerwac(int poczatkeKonczeniaWatkow, int koniecKonczeniaWatkow, Thread[] threads) {
		if (this.arg.imieNazwisko != null && this.poslowie.containsKey(this.arg.imieNazwisko)) {
			for (int i = poczatkeKonczeniaWatkow; i < koniecKonczeniaWatkow; i++) {
				threads[i].interrupt();
			}
			return true;
		}
		return false;
	}

}
