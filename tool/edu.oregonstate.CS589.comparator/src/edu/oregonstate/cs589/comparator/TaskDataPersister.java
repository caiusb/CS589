package edu.oregonstate.cs589.comparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TaskDataPersister {

	private String rootFileName;
	private JSONArray events;
	private String commitDescription;

	public TaskDataPersister(String fileName) {
		this.rootFileName = fileName;
		events = new JSONArray();
	}

	public void persistEvent(JSONObject event) {
		events.add(event);
	}

	public void persistToFile() {
		try {
			writeToFile(rootFileName + "_events", events.toJSONString());
			writeToFile(rootFileName + "_commitDescription", commitDescription);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void persistCommitDescription(String commitDescription) {
		this.commitDescription = commitDescription;
	}

	private void writeToFile(String fileName, String contents) throws IOException {
		Path descriptionFilePath = Activator.getDefault().getLocalStoragePath().resolve(fileName);
		Files.write(descriptionFilePath, contents.getBytes(), StandardOpenOption.CREATE);
	}
}
