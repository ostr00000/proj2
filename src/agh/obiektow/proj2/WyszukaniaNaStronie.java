package agh.obiektow.proj2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WyszukaniaNaStronie implements Runnable {
	Sejm sejm;
	int nrStrony;
	InfromacjeOgolne info = null;

	WyszukaniaNaStronie(Sejm sejm, int nrStrony) {
		this.sejm = sejm;
		this.nrStrony = nrStrony;
	}

	@Override
	public void run() {
		JsonFromUrl dane = new JsonFromUrl(
				"https://api-v3.mojepanstwo.pl/dane/poslowie.json?_type=objects&page=" + Integer.valueOf(nrStrony));
		JSONObject json = dane.pobierzDane();
		JSONArray dataobject = (JSONArray) json.get("Dataobject");
		int size = dataobject.size();
		Posel[] listaPoslow = new Posel[size];
		Thread threads[] = new Thread[size];
		for (int i = 0; i < size; i++) {
			JSONObject index = (JSONObject) dataobject.get(i);
			int id = Integer.valueOf((String) index.get("id"));
			listaPoslow[i] = new Posel(id);
			threads[i] = new Thread(listaPoslow[i]);
			threads[i].run();
		}

		for (int i = 0; i < size; i++) {
			if (czyPrzerwac(i, size, threads))
				return;
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				System.out.println("watek przerwany");
			}
			if (0 == i) {
				info = new InfromacjeOgolne(listaPoslow[0]);
			} else {
				info.sprawdzPosla(listaPoslow[i]);
			}
			sejm.addPosla(listaPoslow[i]);
		}
		System.out.println("zakonczono szukanie na stronie " + this.nrStrony);
	}

	private boolean czyPrzerwac(int poczatkeKonczeniaWatkow, int koniecKonczeniaWatkow, Thread[] threads) {
		if (this.sejm.arg.imieNazwisko != null && this.sejm.poslowie.containsKey(this.sejm.arg.imieNazwisko)) {
			for (int i = poczatkeKonczeniaWatkow; i < koniecKonczeniaWatkow; i++) {
				threads[i].interrupt();
			}
			return true;
		}
		return false;
	}
}
