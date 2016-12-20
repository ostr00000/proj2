package agh.obiektow.proj2;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainProj2 {
	public static void main(String[] args) {
		try {
			Argumenty arg = new Argumenty(args);
			Sejm pobraneDane = new Sejm();
			pobraneDane.szukajPoslow();
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
		} catch (NumberFormatException e) {
			System.out.println("argument 1. musi byc liczba od 1 do 7 wlacznie");
		} catch (MalformedURLException e) {
			System.out.println("problem ze strona");
		} catch (IOException e) {
			System.out.println("problem ze odczytem");
		}
	}
}
