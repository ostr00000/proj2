package agh.obiektow.proj2;

public class Argumenty {
	TypInfo funkcja;	
	String imie=null;
	String nazwisko=null;
	
	public Argumenty(String [] args){
		if(args.length<1)
			throw new ArrayIndexOutOfBoundsException("Podaj przynajmniej 1 argument");
		int wybranyNumer=Integer.valueOf(args[0]);
		if(wybranyNumer>7)
			throw new NumberFormatException();
		if(wybranyNumer==1 || wybranyNumer==2){
			if(args.length<3)
				throw new ArrayIndexOutOfBoundsException("Podaj 3 parametry \" nrFunkcji Imie Nazwisko \"");
			if(wybranyNumer==1){
				funkcja=TypInfo.sumaWydatkow;
			}else{
				funkcja=TypInfo.drobneNaprawy;
			}
			imie=args[1];
			nazwisko=args[2];
		}else
		if(wybranyNumer==3){
			funkcja=TypInfo.sredniaWydatkowPoslow;
		}else
		if(wybranyNumer==4){
			funkcja=TypInfo.najwiecejPodrozyZagranicznych;
		}else
		if(wybranyNumer==5){
			funkcja=TypInfo.najdluzejPrzebywalZaGranica;
		}else
		if(wybranyNumer==6){
			funkcja=TypInfo.najdrozszaPodrozZagraniczna;
		}else
		if(wybranyNumer==7){
			funkcja=TypInfo.odwiedziliWlochy;
		}
	}
}
