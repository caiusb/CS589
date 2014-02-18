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
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private final void retrieveTasks() throws IOException {
		String rootRepoPath = "repos";
		String s = File.separator;

		tasks = new ArrayList<>();

		demoTask = (new Task(userID, "demo", "SVN", rootRepoPath + s + "clientRecorder" + s + ".git", "f3c9d1de5c583c95fecd56e66de8a1c84bf8cf40"));
		
		tasks.add(new Task(userID, "T01", "SVN", rootRepoPath + s + "davmail" + s + ".git", "afd4e9539be4181781c2700d780175ad2b294795"));
		tasks.add(new Task(userID, "T02", "SVN", rootRepoPath + s + "vassal" + s + ".git", "1c06a1d92028995868f9b9e54b063ac472442a3b"));
		tasks.add(new Task(userID, "T03", "SVN", rootRepoPath + s + "jmol" + s + ".git", "cb0a86672b9aac08f60ec62ef274f7e8ed80152e"));
		
		tasks.add(new Task(userID, "T04", "Git", rootRepoPath + s + "ActionBarSherlock" + s + ".git", "0f07d2dde53d1445addbebf440c29dc5adbcf539"));
		tasks.add(new Task(userID, "T05", "Git", rootRepoPath + s + "elasticsearch" + s + ".git", "b591d7fef71b53ec1e6123787a913bb1115e10f2"));
		tasks.add(new Task(userID, "T06", "Git", rootRepoPath + s + "SlidingMenu" + s + ".git", "1c1d8df60f47081b60e7f678c3b28f5f862fd76d"));
		
	}

	private void loadTasksFromProperties() throws IOException {
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
