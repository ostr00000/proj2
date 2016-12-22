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
			threads[i].run();
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
		System.out.println("zakonczono szukanie");
	}

	public void addPosla(Posel kolejny) {
		this.poslowie.put(kolejny.imieNazwisko, kolejny);
	}

	public void wypisz() {
		String napis;
		switch (this.arg.funkcja) {
		case drobneNaprawy:
		case sumaWydatkow:
			if (!this.poslowie.containsKey(this.arg.imieNazwisko)) {
				napis = "Brak danych dla posla " + this.arg.imieNazwisko + " podczas " + this.arg.kadencja
						+ " kadencji";
				break;
			}
			if (this.arg.funkcja == TypInfo.drobneNaprawy) {
				napis = "Posel " + this.arg.imieNazwisko + " wydal na 'drobne naprawy i remonty biura poselskiego':"
						+ this.poslowie.get(this.arg.imieNazwisko).wydatkiDrobneNaprawy;
			} else {
				napis = "Posel " + this.arg.imieNazwisko + " wydal lacznie :"
						+ this.poslowie.get(this.arg.imieNazwisko).sumaWydatkow;
			}
			break;
		case najdrozszaPodrozZagraniczna:
			napis = "Najdrozsza podroz za granice odbyl posel" + this.info.maxCenaPodroz.imieNazwisko
					+ ", podroz kosztowala " + this.info.maxCenaPodroz.najdrozszaPodroz;
			break;
		case najwiecejPodrozyZagranicznych:
			napis = "Najwiecej podrozy za granice odbyl posel" + this.info.maxWyjazdow.imieNazwisko
			+ ", odbyl on " + this.info.maxWyjazdow.liczbaWyjazdow+" wyjazdow";
			break;
		case najdluzejPrzebywalZaGranica:
			napis = "Najdluzej za granica przebywal posel" + this.info.najduzejZaGranica.imieNazwisko
			+ ", przbywal on " + this.info.najduzejZaGranica.dniZaGranica+" dni";
			break;
		case sredniaWydatkowPoslow:
			napis = "Srednia wydatkow dla posla wynosi "+ (this.info.sumaSumWydatkow/this.info.liczbaPoslow);
			break;
		case odwiedziliWlochy:
			StringBuilder build=new StringBuilder();
			for(Posel p:poslowie.values()){
				build.append(p.imieNazwisko).append(", ");
			}
			napis = "Lista poslow, ktorzy odwiedzli Wlochy: "+build.toString();
			break;
		default:
			napis="";
			break;
		}
		System.out.println(napis);
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
