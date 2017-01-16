package agh.obiektow.proj2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WyszukaniaNaStronie implements Runnable {
	private Sejm sejm;
	private int nrStrony;
	private InfromacjeOgolne info = null;

	public WyszukaniaNaStronie(Sejm sejm, int nrStrony) {
		this.sejm = sejm;
		this.nrStrony = nrStrony;
	}

	public InfromacjeOgolne getInfo() {
		return info;
	}

	public Sejm getSejm() {
		return sejm;
	}

	@Override
	public void run() {
		JsonFromUrl dane = new JsonFromUrl(
				"https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions%5Bposlowie.kadencja%5D="+this.sejm.getArg().getKadencja() + "&_type=objects&page=" + Integer.valueOf(nrStrony));
		JSONObject json = dane.pobierzDane();
		
		JSONArray dataobject = (JSONArray) json.get("Dataobject");
		int size = dataobject.size();
		Posel[] listaPoslow = new Posel[size];
		Thread threads[] = new Thread[size];
		
		for (int i = 0; i < size; i++) {
			if (this.sejm.czyPrzerwac(0, i, threads))
				return;
			
			JSONObject index = (JSONObject) dataobject.get(i);
			int id = Integer.valueOf((String) index.get("id"));
			
			listaPoslow[i] = new Posel(id, this.sejm);
			threads[i] = new Thread(listaPoslow[i]);
			threads[i].start();
		}

		for (int i = 0; i < size; i++) {
			if (this.sejm.czyPrzerwac(i, size, threads))
				return;
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				if (this.sejm.czyPrzerwac(i+1, size, threads))
					return;
				System.out.println("watek przerwany");// jesli watek przerwany to juz znaleziono
			}
			if (0 == i) {
				this.info = new InfromacjeOgolne(listaPoslow[0]);
			} else {
				this.info.sprawdzPosla(listaPoslow[i]);
			}
		}
	}
}
