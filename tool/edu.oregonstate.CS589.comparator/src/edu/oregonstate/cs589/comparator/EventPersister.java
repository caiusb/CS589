package edu.oregonstate.cs589.comparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EventPersister {

	private String fileName;
	private JSONArray events;

	public EventPersister(String fileName) {
		this.fileName = fileName;
		events = new JSONArray();
	}

	public void persist(JSONObject obj) {
		events.add(obj);
	}

	public void persistToFile() {
		try {
			Path filePath = Activator.getDefault().getLocalStoragePath().resolve(fileName);
			System.err.println(filePath);
			Files.write(filePath, events.toJSONString().getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
