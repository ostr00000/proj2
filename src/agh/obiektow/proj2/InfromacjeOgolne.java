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
	public InfromacjeOgolne(InfromacjeOgolne left,InfromacjeOgolne right){
		this.maxWyjazdow=(left.maxWyjazdow.liczbaWyjazdy>right.maxWyjazdow.liczbaWyjazdy)?left.maxWyjazdow:right.maxWyjazdow;
		this.najduzejZaGranica=(left.najduzejZaGranica.liczbaWyjazdy>right.najduzejZaGranica.liczbaWyjazdy)?left.najduzejZaGranica:right.najduzejZaGranica;
		this.maxCenaPodroz=(left.maxCenaPodroz.liczbaWyjazdy>right.maxCenaPodroz.liczbaWyjazdy)?left.maxCenaPodroz:right.maxCenaPodroz;
		this.sumaSumWydatkow=left.sumaSumWydatkow+right.sumaSumWydatkow;
		this.liczbaPoslow= left.liczbaPoslow+right.liczbaPoslow;
	}
	
	void sprawdzPosla(Posel kolejnyPosel){
		if(this.maxWyjazdow.liczbaWyjazdy>kolejnyPosel.liczbaWyjazdy){
			this.maxWyjazdow.liczbaWyjazdy=kolejnyPosel.liczbaWyjazdy;
		}
		if(this.najduzejZaGranica.dniZaGranica>kolejnyPosel.dniZaGranica){
			this.najduzejZaGranica.dniZaGranica=kolejnyPosel.dniZaGranica;
		}
		if(this.najduzejZaGranica.dniZaGranica>kolejnyPosel.dniZaGranica){
			this.najduzejZaGranica.dniZaGranica=kolejnyPosel.dniZaGranica;
		}
		sumaSumWydatkow+=kolejnyPosel.sumaWydatkow;
		liczbaPoslow++;
	}
	
}
