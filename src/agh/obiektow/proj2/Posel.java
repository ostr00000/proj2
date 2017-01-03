package agh.obiektow.proj2;

import org.json.simple.*;

public class Posel implements Runnable {
	private int idPosla;
	private String imieNazwisko;
	private long liczbaWyjazdow;
	private double wyjazdyCena;
	private double wydatkiDrobneNaprawy = 0;
	private double sumaWydatkow = 0;
	private int dniZaGranica = 0;
	private double najdrozszaPodroz = 0;
	private Boolean odwiedzilWlochy = false;
	private Sejm sejm;


	public Posel(int id, Sejm sejm) {
		this.idPosla = id;
		this.sejm = sejm;
	}
	
	public int getIdPosla() {
		return idPosla;
	}

	public String getImieNazwisko() {
		return imieNazwisko;
	}

	public long getLiczbaWyjazdow() {
		return liczbaWyjazdow;
	}

	public double getWyjazdyCena() {
		return wyjazdyCena;
	}

	public double getWydatkiDrobneNaprawy() {
		return wydatkiDrobneNaprawy;
	}

	public double getSumaWydatkow() {
		return sumaWydatkow;
	}

	public int getDniZaGranica() {
		return dniZaGranica;
	}

	public double getNajdrozszaPodroz() {
		return najdrozszaPodroz;
	}

	public Boolean getOdwiedzilWlochy() {
		return odwiedzilWlochy;
	}

	
	public void run() {
		if (Thread.currentThread().isInterrupted())
			return;
		JsonFromUrl dane = new JsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/" + String.valueOf(this.idPosla)
				+ ".json?layers[]=wyjazdy&layers[]=wydatki");
		JSONObject json = dane.pobierzDane();
		if (json == null)
			return;
		JSONObject layers = (JSONObject) json.get("layers");
		JSONObject data = (JSONObject) json.get("data");

		int indexKadencji = wybierzIndex(data);

		ustawImieNazwisko(data);
		ustawLiczbaWyjazdow(data);
		ustawWyjazdyCena(data);

		obliczWydatki(layers, indexKadencji);
		obliczPodroze(layers);
		this.sejm.addPosla(this);
	}

	private int wybierzIndex(JSONObject data) {
		JSONArray tabKadencja = (JSONArray) data.get("poslowie.kadencja");
		for (int i = 0; i < tabKadencja.size(); i++) {
			long index = (long) tabKadencja.get(i);
			if (index == this.sejm.getArg().getKadencja())
				return i;
		}
		System.out.println("blad");
		return -1;

	}

	private void ustawWyjazdyCena(JSONObject data) {
		Object test = data.get("poslowie.wartosc_wyjazdow");// problem z 0
		if (test instanceof Double) {
			this.wyjazdyCena = (double) test;
		} else if (test instanceof Long) {
			long tmp = (long) test;
			this.wyjazdyCena = (double) tmp;
		} else
			throw new ClassCastException();
	}

	private void ustawLiczbaWyjazdow(JSONObject data) {
		this.liczbaWyjazdow = (long) data.get("poslowie.liczba_wyjazdow");
	}

	private void ustawImieNazwisko(JSONObject data) {
		this.imieNazwisko = (String) data.get("poslowie.nazwa");
	}

	private void obliczPodroze(JSONObject layers) {
		Object test = layers.get("wyjazdy");
		
		// sa 2 mozliwosci przy ktorych wystapi blad: nie odbyl zadnych
		// wyjazdow, albo nie ma danych owyjazdach
		if (0 == this.liczbaWyjazdow || !(test instanceof JSONArray)) {
			this.dniZaGranica = 0;
			this.najdrozszaPodroz = 0;
			return;
		}
		
		JSONArray wyjazdy = (JSONArray) test;
		for (Object index : wyjazdy) {
			JSONObject indexJson = (JSONObject) index;
			
			this.dniZaGranica += Integer.valueOf((String) indexJson.get("liczba_dni"));
			
			String kraj = (String) indexJson.get("kraj");
			if (kraj.equals("W³ochy")) {
				this.odwiedzilWlochy = true;
			}
			
			double cena = Double.valueOf((String) indexJson.get("koszt_suma"));
			if (cena > this.najdrozszaPodroz) {
				this.najdrozszaPodroz = cena;
			}
		}

	}

	private void obliczWydatki(JSONObject layers, int indexKadencji) {
		JSONObject wydatki = (JSONObject) layers.get("wydatki");
		JSONArray roczniki = (JSONArray) wydatki.get("roczniki");

		// zdarza sie ze dane sa nieuzupelnione
		if (roczniki.size() == 0)
			return;

		JSONObject kadencja = (JSONObject) roczniki.get(indexKadencji);
		JSONArray pola = (JSONArray) kadencja.get("pola");
		for (int i = 0; i < pola.size(); i++) {
			double koszt = Double.valueOf((String) pola.get(i));
			this.sumaWydatkow += koszt;
			if (12 == i) {
				this.wydatkiDrobneNaprawy += koszt;
			}
		}
	}
}
