package edu.oregonstate.cs589.comparator;

import org.json.simple.JSONObject;

public class EventPersister {

	public static void persist(JSONObject obj) {
		System.out.println(obj.toJSONString());
	}

}
