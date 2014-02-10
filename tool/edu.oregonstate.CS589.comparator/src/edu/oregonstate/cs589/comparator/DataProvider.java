package edu.oregonstate.cs589.comparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProvider {

	private static final String USER_ID = "userID";

	private Properties properties;

	private String userID;

	private List<Task> tasks;

	private Task demoTask;

	private static class Instance {
		public static final DataProvider _instance = new DataProvider();
	}

	private DataProvider() {
		properties = new Properties();
		userID = properties.getProperty(USER_ID);

		try {
			retrieveTasks();
			demoTask = builtTaskFromString("demo");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private final void retrieveTasks() throws IOException {
		tasks = new ArrayList<>();

		int i = 1;
		String taskName = "T" + i;

		while (properties.getProperty(taskName) != null) {
			Task task = builtTaskFromString(taskName);
			
			tasks.add(task);

			i++;
			taskName = "T" + i;
		}
	}

	private Task builtTaskFromString(String taskName) throws IOException {
		String[] repoData = properties.getProperty(taskName).trim().split(";");

		Task task = new Task(userID, taskName, repoData[0], repoData[1], repoData[2]);
		return task;
	}

	public static DataProvider getInstance() {
		return Instance._instance;
	}

	public List getTasks() {
		Collections.shuffle(tasks);
		return tasks;
	}

	public Task getDemoTask() {
		return demoTask;
	}
}
