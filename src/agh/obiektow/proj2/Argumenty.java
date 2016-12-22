package agh.obiektow.proj2;

public class Argumenty {
	TypInfo funkcja;
	String imieNazwisko = null;
	int kadencja;

	public static String blad = "Progam wypisuje informacje w zaleznosci od wybranej liczby:\n"
			+ "1 => suma wydatk�w pos�a/pos�anki o okre�lonym imieniu i nazwisku\n"
			+ "2 => wysoko�ci wydatk�w na 'drobne naprawy i remonty biura poselskiego' okre�lonego pos�a/pos�anki\n"
			+ "3 => �redniej warto�ci sumy wydatk�w wszystkich pos��w\n"
			+ "4 => pos�a/pos�anki, kt�ry wykona� najwi�cej podr�y zagranicznych\n"
			+ "5 => pos�a/pos�anki, kt�ry najd�u�ej przebywa� za granic�\n"
			+ "6 => pos�a/pos�anki, kt�ry odby� najdro�sz� podr� zagraniczn�\n"
			+ "7 => list� wszystkich pos��w, kt�rzy odwiedzili W�ochy";

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
