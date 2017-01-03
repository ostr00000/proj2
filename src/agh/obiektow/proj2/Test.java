package agh.obiektow.proj2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.*;

@SuppressWarnings("unused")

public class Test {
	
	/*@org.junit.Test
	public void test() {
		JSONObject j = (JSONObject) JSONValue.parse("{\"id\":0}");
		System.out.println(j);
		long l = (long) j.get("id");
		System.out.println(l);
		double i = (double) l;
		System.out.println(i);
	}*/

	@org.junit.Test
	public void testArg1() {
		Argumenty arg = new Argumenty(new String[] { "7", "1", "Ala", "Kot" });
		assertEquals(arg.getFunkcja(), TypInfo.sumaWydatkow);
		assertEquals(arg.getKadencja(), 7);
		assertEquals(arg.getImieNazwisko(), "Ala Kot");

	}
	//zla kadencja
	@org.junit.Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testArg2() {
		Argumenty arg = new Argumenty(new String []{"4","1","Ala","Kot"}); 
	}
	
	//zla kadencja
	@org.junit.Test(expected=NumberFormatException.class)
	public void testArg3() {
		Argumenty arg = new Argumenty(new String []{"ma","1","Ala","Kot"}); 
	}
	
	//zla funkcja
	@org.junit.Test(expected=NumberFormatException.class)
	public void testArg4() {
		Argumenty arg = new Argumenty(new String []{"8","ma","Ala","Kot"}); 
	}
	
	//zla funkcja
	@org.junit.Test(expected=NumberFormatException.class)
	public void testArg5() {
		Argumenty arg = new Argumenty(new String []{"8","-3","Ala","Kot"}); 
	}
	
	//zla funkcja
	@org.junit.Test(expected=NumberFormatException.class)
	public void testArg6() {
		Argumenty arg = new Argumenty(new String []{"8","43","Ala","Kot"}); 
	}
	
	//za malo argumentow
	@org.junit.Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testArg7() {
		Argumenty arg = new Argumenty(new String []{"8","1","Ala"}); 
	}
	
	//za malo argumentow
	@org.junit.Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testArg8() {
		Argumenty arg = new Argumenty(new String []{"8","2","Ala"}); 
	}
	
	//wystarczajaco argumentow
	@org.junit.Test
	public void testArg9() {
		Argumenty arg = new Argumenty(new String []{"8","3"});
		assertEquals(arg.getFunkcja(),TypInfo.sredniaWydatkowPoslow);
		assertNotEquals(arg.getKadencja(),7);
	}
	
	@org.junit.Test
	public void testArg10() {
		Argumenty arg = new Argumenty(new String []{"8","2","Ala","Kot"});
		assertEquals(arg.getFunkcja(),TypInfo.drobneNaprawy);
		assertEquals(arg.getKadencja(),8);
		assertEquals(arg.getImieNazwisko(),"Ala Kot");
	}
	@org.junit.Test
	public void testArg11() {
		Argumenty arg = new Argumenty(new String []{"8","4","Ala","Kot"});
		assertEquals(arg.getFunkcja(),TypInfo.najwiecejPodrozyZagranicznych);
		assertEquals(arg.getKadencja(),8);
		assertEquals(arg.getImieNazwisko(),null);
	}
	@org.junit.Test
	public void testArg12() {
		Argumenty arg = new Argumenty(new String []{"7","5","Ala","ma","Kot"});
		assertEquals(arg.getFunkcja(),TypInfo.najdluzejPrzebywalZaGranica);
		assertEquals(arg.getKadencja(),7);
		assertEquals(arg.getImieNazwisko(),null);
	}
	@org.junit.Test
	public void testArg13() {
		Argumenty arg = new Argumenty(new String []{"7","6","Ala","ma","Kot"});
		assertEquals(arg.getFunkcja(),TypInfo.najdrozszaPodrozZagraniczna);
		assertEquals(arg.getKadencja(),7);
		assertEquals(arg.getImieNazwisko(),null);
	}
	@org.junit.Test
	public void testArg14() {
		Argumenty arg = new Argumenty(new String []{"7","7"});
		assertEquals(arg.getFunkcja(),TypInfo.odwiedziliWlochy);
		assertEquals(arg.getKadencja(),7);
		assertEquals(arg.getImieNazwisko(),null);
	}
	@org.junit.Test
	public void testJsonFromUrl() {
		String url=	"https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions%5Bposlowie.kadencja%5D=8&_type=objects&page=11";
		String ostatnia="https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions%5Bposlowie.kadencja%5D=8&_type=objects&page=10";
		JsonFromUrl json=new JsonFromUrl(url);
		JSONObject obj=json.pobierzDane();
		JSONObject links = (JSONObject) obj.get("Links");
		String last=(String) links.get("last");
		assertEquals(last,ostatnia);
	}
	
	@org.junit.Test()
	public void testSejm() throws MalformedURLException, IOException, InterruptedException {
		Sejm localSejm=new Sejm(new Argumenty(new String []{"7","3"}));
		localSejm.szukajPoslow();
		
		for(int i=1;i<8;i++){
			Wiadomosc wiad=new Wiadomosc(new Argumenty(new String []{"7",String.valueOf(i),"Anna","Ba\u0144kowska"}),localSejm);
			wiad.wypisz();
			if(i==2){
				assertTrue(wiad.zwrocWartosc().equals("1978.38"));
			}
		}
		Wiadomosc wiad=new Wiadomosc(new Argumenty(new String []{"7","1","Ewa","Kopacz"}),localSejm);
		wiad.wypisz();
		wiad=new Wiadomosc(new Argumenty(new String []{"7","2","Ewa","Kopacz"}),localSejm);
		wiad.wypisz();
		wiad=new Wiadomosc(new Argumenty(new String []{"7","1","Donald","Tusk"}),localSejm);
		wiad.wypisz();
		wiad=new Wiadomosc(new Argumenty(new String []{"7","2","Donald","Tusk"}),localSejm);
		wiad.wypisz();
		wiad=new Wiadomosc(new Argumenty(new String []{"7","1","Andrzej","Duda"}),localSejm);
		wiad.wypisz();
		wiad=new Wiadomosc(new Argumenty(new String []{"7","2","Andrzej","Duda"}),localSejm);
		wiad.wypisz();
		wiad.wypiszPoslow();
		System.out.println(localSejm.getInfo().getLiczbaPoslow());
	}
}
