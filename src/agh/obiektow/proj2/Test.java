package agh.obiektow.proj2;

import static org.junit.Assert.*;

import org.json.simple.*;

public class Test {

	@org.junit.Test
	public void test() {
		JSONObject j= (JSONObject) JSONValue.parse("{\"id\":0}");
		System.out.println(j);
		long l = (long) j.get("id");
		double i=(double) l;
		System.out.println(i);
		
	}

}
