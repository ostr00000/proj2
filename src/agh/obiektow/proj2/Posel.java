package agh.obiektow.proj2;

import org.json.simple.*;

public class Posel implements Runnable {
	int idPosla;
	String imieNazwisko;
	long liczbaWyjazdow;
	double wyjazdyCena;
	double wydatkiDrobneNaprawy = 0;
	double sumaWydatkow = 0;
	int dniZaGranica = 0;
	double najdrozszaPodroz = 0;
	Boolean odwiedzilWlochy = false;

	Posel(int id) {
		this.idPosla = id;
	}

	public void run() {
		JsonFromUrl dane = new JsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/" + String.valueOf(this.idPosla)
				+ ".json?layers[]=wyjazdy&layers[]=wydatki");
		JSONObject json = dane.pobierzDane();
		if (json == null)
			return;// poprawic?
		JSONObject layers = (JSONObject) json.get("layers");
		JSONObject data = (JSONObject) json.get("data");

		ustawImieNazwisko(data);
		ustawLiczbaWyjazdow(data);
		ustawWyjazdyCena(data);

		obliczWydatki(layers);
		obliczPodroze(layers);
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

	private void obliczWydatki(JSONObject layers) {
		JSONObject wydatki = (JSONObject) layers.get("wydatki");
		JSONArray roczniki = (JSONArray) wydatki.get("roczniki");

		for (Object index : roczniki) {
			JSONObject indexJson = (JSONObject) index;
			JSONArray pola = (JSONArray) indexJson.get("pola");
			for (int i = 0; i < pola.size(); i++) {
				double koszt = Double.valueOf((String) pola.get(i));
				this.sumaWydatkow += koszt;
				if (12 == i) {
					this.wydatkiDrobneNaprawy += koszt;
				}
			}
		}
	}

}
