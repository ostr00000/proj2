package agh.obiektow.proj2;

public class Argumenty {
	TypInfo funkcja;
	String imieNazwisko = null;
	int kadencja;

	public static String blad = "Progam wypisuje informacje w zaleznosci od wybranej liczby:\n"
			+ "1 => suma wydatków pos³a/pos³anki o okreœlonym imieniu i nazwisku\n"
			+ "2 => wysokoœci wydatków na 'drobne naprawy i remonty biura poselskiego' okreœlonego pos³a/pos³anki\n"
			+ "3 => œredniej wartoœci sumy wydatków wszystkich pos³ów\n"
			+ "4 => pos³a/pos³anki, który wykona³ najwiêcej podró¿y zagranicznych\n"
			+ "5 => pos³a/pos³anki, który najd³u¿ej przebywa³ za granic¹\n"
			+ "6 => pos³a/pos³anki, który odby³ najdro¿sz¹ podró¿ zagraniczn¹\n"
			+ "7 => listê wszystkich pos³ów, którzy odwiedzili W³ochy";

	public Argumenty(String[] args) {
		if (args.length < 2)
			throw new ArrayIndexOutOfBoundsException("Podaj przynajmniej 2 argument");
		kadencja = Integer.valueOf(args[0]);
		if (kadencja < 7 || kadencja > 8)
			throw new ArrayIndexOutOfBoundsException("Mozliwe wybor katdencji to 7 lub 8");
		int wybranaFunkcja = Integer.valueOf(args[1]);
		if (wybranaFunkcja > 7 || wybranaFunkcja <= 0)
			throw new NumberFormatException();
		if (wybranaFunkcja == 1 || wybranaFunkcja == 2) {
			if (args.length < 4)
				throw new ArrayIndexOutOfBoundsException("Podaj 3 parametry \" nrFunkcji Imie Nazwisko \"");
			if (wybranaFunkcja == 1) {
				funkcja = TypInfo.sumaWydatkow;
			} else {
				funkcja = TypInfo.drobneNaprawy;
			}
			imieNazwisko = args[2] + " " + args[3];
		} else if (wybranaFunkcja == 3) {
			funkcja = TypInfo.sredniaWydatkowPoslow;
		} else if (wybranaFunkcja == 4) {
			funkcja = TypInfo.najwiecejPodrozyZagranicznych;
		} else if (wybranaFunkcja == 5) {
			funkcja = TypInfo.najdluzejPrzebywalZaGranica;
		} else if (wybranaFunkcja == 6) {
			funkcja = TypInfo.najdrozszaPodrozZagraniczna;
		} else if (wybranaFunkcja == 7) {
			funkcja = TypInfo.odwiedziliWlochy;
		}
	}
}
