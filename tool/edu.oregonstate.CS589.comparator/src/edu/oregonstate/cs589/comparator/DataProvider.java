package edu.oregonstate.cs589.comparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProvider {

	private static final String ROOT_REPOS_PATH = "rootReposPath";

	private static final String USER_ID = "userID";

	private String userID;
	private Task demoTask;
	private String rootReposPath;
	private List<Task> tasks;

	private Task practiceTask;

	private static class Instance {
		public static final DataProvider _instance = new DataProvider();
	}

	private DataProvider() {
		userID = getUserID();

		try {
			retrieveTasks();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getUserID() {
		return System.currentTimeMillis() + "";
		// return properties.getProperty(USER_ID);
	}

	private final void retrieveTasks() throws IOException {
		String rootRepoPath = "repos";
		String s = "/";

		tasks = new ArrayList<>();

		demoTask = (new Task(userID, "demo", "Git", rootRepoPath + s + "demo" + s + ".git", "2089a6aa7bd5337707456f03efee81bb9883e3c8", 0));

		practiceTask = new Task(userID, "practice", "Git", rootRepoPath + s + "Android-ViewPagerIndicator-slim" + s + ".git", "efb8baf11975e319e29fb0b9cbd0158e50f58659", 10);

		tasks.add(new Task(userID, "T01", "SVN", rootRepoPath + s + "davmail-slim" + s + ".git", "d84e4595b39daad12f6ff8da0a888b0fc9a8e3e5"));
		tasks.add(new Task(userID, "T02", "SVN", rootRepoPath + s + "vassal-slim" + s + ".git", "7cb59e5b1fda9d4f3fa0b4c916a681d5bd41bb52"));
		tasks.add(new Task(userID, "T03", "SVN", rootRepoPath + s + "jmol-slim" + s + ".git", "28bbc301fcf68294195e5042f7368117bf819b16"));

		tasks.add(new Task(userID, "T04", "Git", rootRepoPath + s + "ActionBarSherlock-slim" + s + ".git", "84ef23bc2599456099ea612dc1dadae379ac50c6"));
		tasks.add(new Task(userID, "T05", "Git", rootRepoPath + s + "elasticsearch" + s + ".git", "b591d7fef71b53ec1e6123787a913bb1115e10f2"));
		tasks.add(new Task(userID, "T06", "Git", rootRepoPath + s + "SlidingMenu" + s + ".git", "1c1d8df60f47081b60e7f678c3b28f5f862fd76d"));

	}

	// private void loadTasksFromProperties() throws IOException {
	// int i = 1;
	// String taskName = "T" + i;
	//
	// while (properties.getProperty(taskName) != null) {
	// Task task = builtTaskFromString(taskName);
	//
	// tasks.add(task);
	//
	// i++;
	// taskName = "T" + i;
	// }
	// }
	//
	// private Task builtTaskFromString(String taskName) throws IOException {
	// String[] repoData = properties.getProperty(taskName).trim().split(";");
	//
	// String commitOrigin = repoData[0];
	// String repoPath = rootReposPath + File.separator + repoData[1] +
	// File.separator + ".git";
	// String commitID = repoData[2];
	//
	// Task task = new Task(userID, taskName, commitOrigin, repoPath, commitID);
	// return task;
	// }

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

	public Task getPracticeTask() {
		return practiceTask;
	}
}
