package edu.oregonstate.cs589.comparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {
	private static class Instance {
		public static final DataProvider _instance = new DataProvider();
	}

	private DataProvider() {
	}

	public static DataProvider getInstance() {
		return Instance._instance;
	}

	public List getTasks() {
		List<Task> tasks = new ArrayList<>();
		try {
			tasks.add(new Task("testData/repos/P/.git", "040d292f2ea983a918bd5be9d0242c5dcfff9f38"));
			tasks.add(new Task("testData/repos/P/.git", "1dfed3f41204035f0e6ec29ccf69d55a44274e35"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tasks;
	}
}
