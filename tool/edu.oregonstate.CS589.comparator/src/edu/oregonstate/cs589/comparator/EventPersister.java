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
	private String commitDescription;

	public EventPersister(String fileName) {
		this.fileName = fileName;
		events = new JSONArray();
	}

	public void persist(JSONObject obj) {
		events.add(obj);
	}

	public void persistToFile() {
		try {
			writeToFile(fileName + "_events", events.toJSONString());
			writeToFile(fileName + "_commitDescription", commitDescription);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(String fileName, String contents) throws IOException {
		Path descriptionFilePath = Activator.getDefault().getLocalStoragePath().resolve(fileName);
		Files.write(descriptionFilePath, contents.getBytes(), StandardOpenOption.CREATE);
	}

	public void persistCommitDescription(String commitDescription) {
		this.commitDescription = commitDescription;
	}

}
