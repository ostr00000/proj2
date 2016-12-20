package agh.obiektow.proj2;


import org.json.simple.*;

public class TworzonyPosel implements Runnable {
	int idPosla;
	String imieNazwisko;
	int liczbaWyjazdow;
	double wyjazdyCena;	
	double wydatkiDrobneNaprawy = 0;
	double sumaWydatkow = 0;
	int dniZaGranica=0;
	double najdrozszaPodroz=0;
	Boolean odwiedzilWlochy=false;
	
	TworzonyPosel(int id) {
		this.idPosla = id;
	}

	public void run() {
		JSONObject json = Sejm.pobierzDane("https://api-v3.mojepanstwo.pl/dane/poslowie/" + String.valueOf(this.idPosla)
				+ ".json?layers[]=wyjazdy&layers[]=wydatki");
		if(json==null)return;//poprawic?
		JSONObject layers = (JSONObject) json.get("layers");
		JSONObject data = (JSONObject) json.get("data");
			
		this.imieNazwisko = (String) data.get("poslowie.nazwa");
		this.liczbaWyjazdow = (int) data.get("poslowie.liczba_wyjazdow");
		this.wyjazdyCena = (double) data.get("poslowie.wartosc_wyjazdow");
		
		obliczWydatki(layers);
		obliczPodroze(layers);
	}

	private void obliczPodroze(JSONObject layers) {
		JSONArray wyjazdy = (JSONArray) layers.get("wydatki");
		for(Object index:wyjazdy){
			JSONObject indexJson = (JSONObject) index;
			this.dniZaGranica += (int) indexJson.get("liczba_dni");
			String kraj = (String) indexJson.get("kraj");
			if(kraj.equals("W³ochy")){
				this.odwiedzilWlochy=true;
			}
			double cena= (double) indexJson.get("koszt_suma");
			if(cena>this.najdrozszaPodroz){
				this.najdrozszaPodroz=cena;
			}
		}
		
	}

	private void obliczWydatki(JSONObject layers) {
		JSONObject wydatki = (JSONObject) layers.get("wydatki");
		JSONArray roczniki = (JSONArray) wydatki.get("roczniki");

		for (Object index : roczniki) {
			JSONObject indexJson = (JSONObject) index;
			JSONArray pola = (JSONArray) indexJson.get("pola");
			for (int i=0;i<pola.size();i++) {
				this.sumaWydatkow += (double) pola.get(i);
				if(12==i){
					this.wydatkiDrobneNaprawy+= (double) pola.get(i);
				}
			}
		}
	}

}
