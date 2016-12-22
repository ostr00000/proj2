package agh.obiektow.proj2;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainProj2 {
	public static void main(String[] args) {
		try {
			Argumenty arg = new Argumenty(args);
			Sejm pobraneDane = new Sejm(arg);
			pobraneDane.szukajPoslow();
			pobraneDane.wypisz();

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
			System.out.println(Argumenty.blad);
		} catch (NumberFormatException e) {
			System.out.println("argument 1. musi byc liczba od 1 do 7 wlacznie");
			System.out.println(Argumenty.blad);
		} catch (MalformedURLException e) {
			System.out.println("problem ze strona");
		} catch (IOException e) {
			System.out.println("problem ze odczytem");
		}catch(ArithmeticException e){
			System.out.println("blad danych - brak poslow");
		//} catch (ClassCastException e) {
		//	System.out.println("problem z typem w json\n" + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("watek przerwany");
		}
	}
}
