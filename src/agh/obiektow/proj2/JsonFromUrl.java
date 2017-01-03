package agh.obiektow.proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonFromUrl {
	private String url;

	public JsonFromUrl(String url) {
		this.url = url;
	}

	public JSONObject pobierzDane(){
		try{
			return this.pobierzDane(2);
		}
		catch (MalformedURLException e) {
			System.out.println("problem z adresem: " + this.url);
		} catch (IOException e) {
			System.out.println("problem z odczytem przy pobieranu danych ze strony: " + this.url);
		}
		return null;
	}
	
	private JSONObject pobierzDane(int ileRazy) throws MalformedURLException, IOException{
		try{
			return this.pobierz();
		}catch(IOException e){
			if(ileRazy!=0){
				return this.pobierzDane(ileRazy-1);
			}else throw e;
		}
	}
	
	private JSONObject pobierz() throws MalformedURLException, IOException  {
		try (Scanner scan = new Scanner(
				new BufferedReader(new InputStreamReader(((new URL(this.url)).openConnection()).getInputStream())))) {
			StringBuilder text = new StringBuilder();
			while (scan.hasNextLine()) {
				text.append(scan.nextLine());
			}
			JSONObject ret = (JSONObject) JSONValue.parse(text.toString());
			return ret;
		} 
	}
}
