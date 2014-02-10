package edu.oregonstate.cs589.comparator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProvider {

	private static final String ROOT_REPOS_PATH = "rootReposPath";

	private static final String USER_ID = "userID";

	private Properties properties;

	private String userID;
	private Task demoTask;
	private String rootReposPath;
	private List<Task> tasks;

	private static class Instance {
		public static final DataProvider _instance = new DataProvider();
	}

	private DataProvider() {
		properties = new Properties();
		userID = properties.getProperty(USER_ID);
		rootReposPath = properties.getProperty(ROOT_REPOS_PATH);

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

		String commitOrigin = repoData[0];
		String repoPath = rootReposPath + File.separator + repoData[1] + File.separator + ".git";
		String commitID = repoData[2];

		Task task = new Task(userID, taskName, commitOrigin, repoPath, commitID);
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
