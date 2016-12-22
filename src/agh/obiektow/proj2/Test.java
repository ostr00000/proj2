package agh.obiektow.proj2;

import static org.junit.Assert.*;

import org.json.simple.*;

public class Test {

	@org.junit.Test
	public void test() {
		JSONObject j = (JSONObject) JSONValue.parse("{\"id\":0}");
		System.out.println(j);
		long l = (long) j.get("id");
		System.out.println(l);
		double i = (double) l;
		System.out.println(i);

	}

	@org.junit.Test
	public void testArg1() {
		Argumenty arg = new Argumenty(new String[] { "7", "1", "Ala", "Kot" });
		assertEquals(arg.funkcja, TypInfo.sumaWydatkow);
		assertEquals(arg.kadencja, 7);
		assertEquals(arg.imieNazwisko, "Ala Kot");

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
		assertEquals(arg.funkcja,TypInfo.sredniaWydatkowPoslow);
		assertNotEquals(arg.kadencja,7);
	}
	
	@org.junit.Test
	public void testArg10() {
		Argumenty arg = new Argumenty(new String []{"8","2","Ala","Kot"});
		assertEquals(arg.funkcja,TypInfo.drobneNaprawy);
		assertEquals(arg.kadencja,8);
		assertEquals(arg.imieNazwisko,"Ala Kot");
	}
	@org.junit.Test
	public void testArg11() {
		Argumenty arg = new Argumenty(new String []{"8","4","Ala","Kot"});
		assertEquals(arg.funkcja,TypInfo.najwiecejPodrozyZagranicznych);
		assertEquals(arg.kadencja,8);
		assertEquals(arg.imieNazwisko,null);
	}
	@org.junit.Test
	public void testArg12() {
		Argumenty arg = new Argumenty(new String []{"7","5","Ala","ma","Kot"});
		assertEquals(arg.funkcja,TypInfo.najdluzejPrzebywalZaGranica);
		assertEquals(arg.kadencja,7);
		assertEquals(arg.imieNazwisko,null);
	}
	@org.junit.Test
	public void testArg13() {
		Argumenty arg = new Argumenty(new String []{"7","6","Ala","ma","Kot"});
		assertEquals(arg.funkcja,TypInfo.najdrozszaPodrozZagraniczna);
		assertEquals(arg.kadencja,7);
		assertEquals(arg.imieNazwisko,null);
	}
	@org.junit.Test
	public void testArg14() {
		Argumenty arg = new Argumenty(new String []{"7","7"});
		assertEquals(arg.funkcja,TypInfo.odwiedziliWlochy);
		assertEquals(arg.kadencja,7);
		assertEquals(arg.imieNazwisko,null);
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
	public void testSejm1() {
		String [] args =new String []{"7","1","Anna","Ba\u0144kowska"};
		MainProj2.main(args);
		
		//assertEquals(arg.funkcja,);

		
	}
}
