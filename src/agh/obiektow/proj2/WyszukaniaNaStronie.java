package agh.obiektow.proj2;

public class WyszukaniaNaStronie implements Runnable {
	Sejm sejm;
	int nrStrony;
	
	WyszukaniaNaStronie(Sejm sejm,int nrStrony){
		this.sejm=sejm;
		this.nrStrony=nrStrony;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
