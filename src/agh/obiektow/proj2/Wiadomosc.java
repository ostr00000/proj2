package agh.obiektow.proj2;

import java.text.DecimalFormat;

public class Wiadomosc {
	private Argumenty arg;
	private Sejm sejm;

	public Wiadomosc(Argumenty arg, Sejm sejm) {
		this.arg = arg;
		this.sejm = sejm;
	}

	public String zwrocWartosc() {
		switch (this.arg.getFunkcja()) {
		case drobneNaprawy:
			return String.valueOf(this.sejm.getPoslowie().get(this.arg.getImieNazwisko()).getWydatkiDrobneNaprawy());
		case najdluzejPrzebywalZaGranica:
			return String.valueOf(this.sejm.getInfo().getNajduzejZaGranica().getDniZaGranica());
		case najdrozszaPodrozZagraniczna:
			return String.valueOf(this.sejm.getInfo().getMaxCenaPodroz().getNajdrozszaPodroz());
		case najwiecejPodrozyZagranicznych:
			return String.valueOf(this.sejm.getInfo().getNajduzejZaGranica().getDniZaGranica());
		case odwiedziliWlochy:
			StringBuilder build = new StringBuilder();
			for (Posel posel : this.sejm.getPoslowie().values()) {
				if (posel.getOdwiedzilWlochy())
					build.append(posel.getImieNazwisko()).append(", ");
			}
			return build.toString();
		case sredniaWydatkowPoslow:
			return String.valueOf((this.sejm.getInfo().getSumaSumWydatkow() / this.sejm.getInfo().getLiczbaPoslow()));
		case sumaWydatkow:
			return String.valueOf(this.sejm.getPoslowie().get(this.arg.getImieNazwisko()).getSumaWydatkow());
		default:
			return null;

		}
	}

	public void wypisz() {
		String napis;
		DecimalFormat df = new DecimalFormat("#.00");
		switch (this.arg.getFunkcja()) {
		case drobneNaprawy:
		case sumaWydatkow:
			if (!this.sejm.getPoslowie().containsKey(this.arg.getImieNazwisko())) {
				napis = "Brak danych dla posla " + this.arg.getImieNazwisko() + " podczas " + this.arg.getKadencja()
						+ " kadencji";
				break;
			}
			if (this.arg.getFunkcja() == TypInfo.drobneNaprawy) {
				napis = "Posel " + this.arg.getImieNazwisko()
						+ " wydal na 'drobne naprawy i remonty biura poselskiego': "
						+ df.format(this.sejm.getPoslowie().get(this.arg.getImieNazwisko()).getWydatkiDrobneNaprawy());
			} else {
				napis = "Posel " + this.arg.getImieNazwisko() + " wydal lacznie: "
						+ df.format(this.sejm.getPoslowie().get(this.arg.getImieNazwisko()).getSumaWydatkow());
			}
			break;
		case najdrozszaPodrozZagraniczna:
			napis = "Najdrozsza podroz za granice odbyl posel "
					+ this.sejm.getInfo().getMaxCenaPodroz().getImieNazwisko() + ", podroz kosztowala "
					+ df.format(this.sejm.getInfo().getMaxCenaPodroz().getNajdrozszaPodroz());
			break;
		case najwiecejPodrozyZagranicznych:
			napis = "Najwiecej podrozy za granice odbyl posel " + this.sejm.getInfo().getMaxWyjazdow().getImieNazwisko()
					+ ", odbyl on " + this.sejm.getInfo().getMaxWyjazdow().getLiczbaWyjazdow() + " wyjazdow";
			break;
		case najdluzejPrzebywalZaGranica:
			napis = "Najdluzej za granica przebywal posel "
					+ this.sejm.getInfo().getNajduzejZaGranica().getImieNazwisko() + ", przbywal on "
					+ this.sejm.getInfo().getNajduzejZaGranica().getDniZaGranica() + " dni";
			break;
		case sredniaWydatkowPoslow:
			napis = "Srednia suma wydatkow dla poslow wynosi "
					+ df.format((this.sejm.getInfo().getSumaSumWydatkow() / this.sejm.getInfo().getLiczbaPoslow()));
			break;
		case odwiedziliWlochy:
			StringBuilder build = new StringBuilder();
			for (Posel posel : this.sejm.getPoslowie().values()) {
				if (posel.getOdwiedzilWlochy())
					build.append(posel.getImieNazwisko()).append(", ");
			}
			napis = "Lista poslow, ktorzy odwiedzli Wlochy: " + build.toString();
			break;
		default:
			napis = "";
			break;
		}
		System.out.println(napis);
	}

	public void wypiszPoslow() {
		System.out.print("lista poslow " + this.arg.getKadencja() + ". kadencji");
		for (Posel posel : this.sejm.getPoslowie().values()) {
			System.out.println(posel.getIdPosla() + " = " + posel.getImieNazwisko());
		}
	}
}
