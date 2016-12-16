package agh.obiektow.proj2;

public class Posel {
	String imie;
	String nazwisko;
	double sumaWydatkow;//
	double wydatkiDrobneNaprawy;
	int liczbaPodruzyZagranicznych;//
	int liczbaDniZaGranica;//
	double najdrozszaPodroz;//
	Boolean odwiedzilWlochy;
	
	public Posel(String imie, String nazwisko, double sumaWydatkow, double wydatkiDrobneNaprawy,
			int liczbaPodruzyZagranicznych, int liczbaDniZaGranica, double najdrozszaPodroz, Boolean odwiedzilWlochy) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.sumaWydatkow = sumaWydatkow;
		this.wydatkiDrobneNaprawy = wydatkiDrobneNaprawy;
		this.liczbaPodruzyZagranicznych = liczbaPodruzyZagranicznych;
		this.liczbaDniZaGranica = liczbaDniZaGranica;
		this.najdrozszaPodroz = najdrozszaPodroz;
		this.odwiedzilWlochy = odwiedzilWlochy;
	}
	
}
