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
	String url; 
	
	JsonFromUrl(String url){
		this.url=url;
	}
	
	public JSONObject pobierzDane() {
		try (Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(((new URL(this.url)).openConnection()).getInputStream() )))) {
			StringBuilder text = new StringBuilder();
			while (scan.hasNextLine()) {
				text.append(scan.nextLine());
			}
			JSONObject ret = (JSONObject) JSONValue.parse(text.toString());
			return ret;
		} catch (MalformedURLException e) {
			System.out.println("problem z url");
		} catch (IOException e) {
			System.out.println("problem z odczytem przy pobieranu danych ze strony:" + url);
		}
		return null;// poprawic?
	}
}
