package agh.obiektow.proj2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.*;

public class Sejm {
	private Argumenty arg;
	private HashMap<String, Posel> poslowie;
	private InfromacjeOgolne info = null;

	public Sejm(Argumenty arg) {
		this.arg = arg;
		this.poslowie = new HashMap<String, Posel>();
	}
	
	public Argumenty getArg() {
		return arg;
	}

	public HashMap<String, Posel> getPoslowie() {
		return poslowie;
	}

	public InfromacjeOgolne getInfo() {
		return info;
	}

	public void szukajPoslow() throws MalformedURLException, IOException, InterruptedException {
		JsonFromUrl dane = new JsonFromUrl(
				"https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=" + arg.getKadencja());
		JSONObject json = dane.pobierzDane();
		
		int liczbaStron = ileStron(json);
		WyszukaniaNaStronie[] wyszukania = new WyszukaniaNaStronie[liczbaStron];
		Thread threads[] = new Thread[liczbaStron];
		
		for (int i = 0; i < liczbaStron; i++) {
			if (czyPrzerwac(0, i, threads)) {
				return;
			}
			wyszukania[i] = new WyszukaniaNaStronie(this, i+1);
			threads[i] = new Thread(wyszukania[i]);
			threads[i].start();
		}
		for (int i = 0; i < liczbaStron; i++) {
			if (czyPrzerwac(i, liczbaStron , threads))
				return;
			threads[i].join();
			if (0 == i) {
				this.info = wyszukania[0].getInfo();
			} else {
				this.info.modyfikuj(wyszukania[i].getInfo());
			}
		}
	}

	public void addPosla(Posel kolejny) {
		this.poslowie.put(kolejny.getImieNazwisko(), kolejny);
	}
	
	public boolean czyPrzerwac(int poczatkeKonczeniaWatkow, int koniecKonczeniaWatkow, Thread[] threads) {
		if (this.arg.getImieNazwisko() != null && this.poslowie.containsKey(this.arg.getImieNazwisko())) {
			for (int i = poczatkeKonczeniaWatkow; i < koniecKonczeniaWatkow; i++) {
				threads[i].interrupt();
			}
			return true;
		}
		return false;
	}

	private int ileStron(JSONObject json) throws IOException {
		JSONObject links = (JSONObject) json.get("Links");
		String lastPage = (String) links.get("last");
		Pattern liczbaNaKoncu = Pattern.compile("(\\d+)$");
		Matcher dopasowane = liczbaNaKoncu.matcher(lastPage);
		if (!dopasowane.find())
			throw new IOException("nie mozna dowiedziec sie ile jest stron");
		return Integer.valueOf(lastPage.substring(dopasowane.start()));
	}


}
