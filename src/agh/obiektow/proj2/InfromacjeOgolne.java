package agh.obiektow.proj2;

public class InfromacjeOgolne {
	Posel maxWyjazdow;
	Posel najduzejZaGranica;
	Posel maxCenaPodroz;
	double sumaSumWydatkow;
	int liczbaPoslow;
	
	public InfromacjeOgolne(Posel pierwszyPosel) {
		this.maxWyjazdow = pierwszyPosel;
		this.najduzejZaGranica = pierwszyPosel;
		this.maxCenaPodroz = pierwszyPosel;
		this.sumaSumWydatkow = pierwszyPosel.sumaWydatkow;
		liczbaPoslow=1;
	}
	
	
	public void modyfikuj(InfromacjeOgolne doPorownania){
		this.maxWyjazdow=choseMaxWyjazdow(this.maxWyjazdow,doPorownania.maxWyjazdow);
		this.najduzejZaGranica=choseNajduzejZaGranica(this.najduzejZaGranica,doPorownania.najduzejZaGranica);
		this.maxCenaPodroz=choseMaxCenaPodroz(this.maxCenaPodroz,doPorownania.maxCenaPodroz);
		this.sumaSumWydatkow=this.sumaSumWydatkow+doPorownania.sumaSumWydatkow;
		this.liczbaPoslow= this.liczbaPoslow+doPorownania.liczbaPoslow;
	}
	
	void sprawdzPosla(Posel kolejnyPosel){
		this.maxWyjazdow=choseMaxWyjazdow(this.maxWyjazdow,kolejnyPosel);
		this.najduzejZaGranica=choseNajduzejZaGranica(this.najduzejZaGranica,kolejnyPosel);
		this.maxCenaPodroz=choseMaxCenaPodroz(this.maxCenaPodroz,kolejnyPosel);
		sumaSumWydatkow+=kolejnyPosel.sumaWydatkow;
		liczbaPoslow++;
	}
	
	private Posel choseMaxWyjazdow(Posel left,Posel right){
		return (left.liczbaWyjazdow>right.liczbaWyjazdow)?left:right;
	}
	private Posel choseNajduzejZaGranica(Posel left,Posel right){
		return (left.dniZaGranica>right.dniZaGranica)?left:right;
	}
	private Posel choseMaxCenaPodroz(Posel left,Posel right){
		return (left.najdrozszaPodroz>right.najdrozszaPodroz)?left:right;
	}
}
