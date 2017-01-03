package agh.obiektow.proj2;

public class InfromacjeOgolne {
	private Posel maxWyjazdow;
	private Posel najduzejZaGranica;
	private Posel maxCenaPodroz;
	private double sumaSumWydatkow;
	private int liczbaPoslow;

	public InfromacjeOgolne(Posel pierwszyPosel) {
		this.maxWyjazdow = pierwszyPosel;
		this.najduzejZaGranica = pierwszyPosel;
		this.maxCenaPodroz = pierwszyPosel;
		this.sumaSumWydatkow = pierwszyPosel.getSumaWydatkow();
		liczbaPoslow = 1;
	}

	public Posel getMaxWyjazdow() {
		return maxWyjazdow;
	}

	public Posel getNajduzejZaGranica() {
		return najduzejZaGranica;
	}

	public Posel getMaxCenaPodroz() {
		return maxCenaPodroz;
	}

	public double getSumaSumWydatkow() {
		return sumaSumWydatkow;
	}

	public int getLiczbaPoslow() {
		return liczbaPoslow;
	}

	public void modyfikuj(InfromacjeOgolne doPorownania) {
		this.maxWyjazdow = choseMaxWyjazdow(this.maxWyjazdow, doPorownania.maxWyjazdow);
		this.najduzejZaGranica = choseNajduzejZaGranica(this.najduzejZaGranica, doPorownania.najduzejZaGranica);
		this.maxCenaPodroz = choseMaxCenaPodroz(this.maxCenaPodroz, doPorownania.maxCenaPodroz);
		this.sumaSumWydatkow = this.sumaSumWydatkow + doPorownania.sumaSumWydatkow;
		this.liczbaPoslow = this.liczbaPoslow + doPorownania.liczbaPoslow;
	}

	public void sprawdzPosla(Posel kolejnyPosel) {
		this.maxWyjazdow = choseMaxWyjazdow(this.maxWyjazdow, kolejnyPosel);
		this.najduzejZaGranica = choseNajduzejZaGranica(this.najduzejZaGranica, kolejnyPosel);
		this.maxCenaPodroz = choseMaxCenaPodroz(this.maxCenaPodroz, kolejnyPosel);
		sumaSumWydatkow += kolejnyPosel.getSumaWydatkow();
		liczbaPoslow++;
	}

	private Posel choseMaxWyjazdow(Posel left, Posel right) {
		return (left.getLiczbaWyjazdow() > right.getLiczbaWyjazdow()) ? left : right;
	}

	private Posel choseNajduzejZaGranica(Posel left, Posel right) {
		return (left.getDniZaGranica() > right.getDniZaGranica()) ? left : right;
	}

	private Posel choseMaxCenaPodroz(Posel left, Posel right) {
		return (left.getNajdrozszaPodroz() > right.getNajdrozszaPodroz()) ? left : right;
	}
}
