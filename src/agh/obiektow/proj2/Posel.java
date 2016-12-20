package agh.obiektow.proj2;

import org.json.simple.JSONObject;

public class Posel {
	String imieNazwisko;
	double sumaWydatkow=0;
	double wydatkiDrobneNaprawy;
	int liczbaWyjazdy;
	int dniZaGranica;
	double najdrozszaPodroz;
	Boolean odwiedzilWlochy;
	
	public Posel(String imieNazwisko, double sumaWydatkow, double wydatkiDrobneNaprawy, int podroze, int dniZaGranica,
			double najdrozszaPodroz, Boolean odwiedzilWlochy) {
		this.imieNazwisko = imieNazwisko;
		this.sumaWydatkow = sumaWydatkow;
		this.wydatkiDrobneNaprawy = wydatkiDrobneNaprawy;
		this.liczbaWyjazdy = podroze;
		this.dniZaGranica = dniZaGranica;
		this.najdrozszaPodroz = najdrozszaPodroz;
		this.odwiedzilWlochy = odwiedzilWlochy;
	}
	
	
	
	
}
