package agh.obiektow.proj2;

import java.text.DecimalFormat;

public class Wiadomosc {
	Argumenty arg;
	Sejm sejm;
	
	Wiadomosc(Argumenty arg,Sejm sejm){
		this.arg=arg;
		this.sejm=sejm;
	}
	
	public String zwrocWartosc(){
		switch(this.arg.funkcja){
		case drobneNaprawy:
			return String.valueOf(this.sejm.poslowie.get(this.arg.imieNazwisko).wydatkiDrobneNaprawy);
		case najdluzejPrzebywalZaGranica:
			return String.valueOf(this.sejm.info.najduzejZaGranica.dniZaGranica);
		case najdrozszaPodrozZagraniczna:
			return String.valueOf(this.sejm.info.maxCenaPodroz.najdrozszaPodroz);
		case najwiecejPodrozyZagranicznych:
			return String.valueOf(this.sejm.info.najduzejZaGranica.dniZaGranica);
		case odwiedziliWlochy:
			StringBuilder build=new StringBuilder();
			for(Posel p:this.sejm.poslowie.values()){
				build.append(p.imieNazwisko).append(", ");
			}
			return build.toString();
		case sredniaWydatkowPoslow:
			return String.valueOf((this.sejm.info.sumaSumWydatkow/this.sejm.info.liczbaPoslow));
		case sumaWydatkow:
			return String.valueOf(this.sejm.poslowie.get(this.arg.imieNazwisko).sumaWydatkow);
		default:
			return null;
		
		}
	}
	
	public void wypisz() {
		String napis;
		DecimalFormat df = new DecimalFormat("#.00"); 
		switch (this.arg.funkcja) {
		case drobneNaprawy:
		case sumaWydatkow:
			if (!this.sejm.poslowie.containsKey(this.arg.imieNazwisko)) {
				napis = "Brak danych dla posla " + this.arg.imieNazwisko + " podczas " + this.arg.kadencja
						+ " kadencji";
				break;
			}
			if (this.arg.funkcja == TypInfo.drobneNaprawy) {
				napis = "Posel " + this.arg.imieNazwisko + " wydal na 'drobne naprawy i remonty biura poselskiego': "
						+ df.format(this.sejm.poslowie.get(this.arg.imieNazwisko).wydatkiDrobneNaprawy);
			} else {
				napis = "Posel " + this.arg.imieNazwisko + " wydal lacznie: "
						+ df.format(this.sejm.poslowie.get(this.arg.imieNazwisko).sumaWydatkow);
			}
			break;
		case najdrozszaPodrozZagraniczna:
			napis = "Najdrozsza podroz za granice odbyl posel " + this.sejm.info.maxCenaPodroz.imieNazwisko
					+ ", podroz kosztowala " + df.format(this.sejm.info.maxCenaPodroz.najdrozszaPodroz);
			break;
		case najwiecejPodrozyZagranicznych:
			napis = "Najwiecej podrozy za granice odbyl posel " + this.sejm.info.maxWyjazdow.imieNazwisko
			+ ", odbyl on " + this.sejm.info.maxWyjazdow.liczbaWyjazdow+" wyjazdow";
			break;
		case najdluzejPrzebywalZaGranica:
			napis = "Najdluzej za granica przebywal posel " + this.sejm.info.najduzejZaGranica.imieNazwisko
			+ ", przbywal on " + this.sejm.info.najduzejZaGranica.dniZaGranica+" dni";
			break;
		case sredniaWydatkowPoslow:
			napis = "Srednia wydatkow dla posla wynosi "+ df.format((this.sejm.info.sumaSumWydatkow/this.sejm.info.liczbaPoslow));
			break;
		case odwiedziliWlochy:
			StringBuilder build=new StringBuilder();
			for(Posel p:this.sejm.poslowie.values()){
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
}
