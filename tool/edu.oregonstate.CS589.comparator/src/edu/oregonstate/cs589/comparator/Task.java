package edu.oregonstate.cs589.comparator;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.json.simple.JSONObject;

import edu.oregonstate.cs589.comparator.rcp.Activator;

public class Task implements Closeable {

	private Repository repository;
	private RevCommit targetCommit;
	private RevCommit parentCommit;

	private String userID;
	private String taskID;
	private String commitOrigin;
	private int taskTimeInMinutes;

	private TaskDataPersister eventPersister;

	/**
	 * assumes a task time of 15 minutes
	 * @param userID
	 * @param taskID
	 * @param commitOrigin
	 * @param repoPath
	 * @param targetCommitID
	 * @throws IOException
	 */
	public Task(String userID, String taskID, String commitOrigin,
			String repoPath, String targetCommitID) throws IOException {
		this(userID, taskID, commitOrigin, repoPath, targetCommitID, 15);
	}

	public Task(String userID, String taskID, String commitOrigin,
			String repoPath, String targetCommitID, int taskTimeInMinutes)
			throws IOException {
		this.userID = userID;
		this.taskID = taskID;
		this.commitOrigin = commitOrigin;
		this.taskTimeInMinutes = taskTimeInMinutes;

		eventPersister = new TaskDataPersister(userID + "_" + taskID);

		initRepositoryData(repoPath, targetCommitID);
	}

	private final void initRepositoryData(String repoPath, String targetCommitID) throws IOException, MissingObjectException, IncorrectObjectTypeException, AmbiguousObjectException {
		File repoFile = Activator.getDefault().getProjectFile(repoPath);
		repository = new FileRepository(repoFile);

		RevWalk rw = new RevWalk(repository);

		targetCommit = rw.parseCommit(repository.resolve(targetCommitID));

		RevCommit[] parents = targetCommit.getParents();

		if (parents.length != 1)
			throw new RuntimeException("Commit must have only one parent!");

		parentCommit = parents[0];
	}

	public String getTargetCommitID() {
		return targetCommit.getName();
	}

	public String getParentCommitID() {
		return parentCommit.getName();
	}

	public Repository getRepository() {
		return repository;
	}

	public String getCommitMessage() {
		return targetCommit.getFullMessage();
	}

	public void recordTaskStart() {
		JSONObject obj = createCommonJSON(JSONConstants.EVENT_TASK_START);

		eventPersister.persistEvent(obj);
	}

	public void recordTaskEnd() {
		JSONObject obj = createCommonJSON(JSONConstants.EVENT_TASK_END);

		eventPersister.persistEvent(obj);
	}

	public void recordDescriptionChange(String oldText, String newText) {
		JSONObject obj = createCommonJSON(JSONConstants.EVENT_DESCRIPTION_TYPE);

		eventPersister.persistEvent(obj);
	}

	private JSONObject createCommonJSON(String type) {
		JSONObject obj = new JSONObject();
		
		obj.put(JSONConstants.JSON_USER_ID, userID);
		obj.put(JSONConstants.JSON_TASK_ID, taskID);
		obj.put(JSONConstants.JSON_COMMIT_ORIGIN, commitOrigin);
		obj.put(JSONConstants.JSON_TIME, System.currentTimeMillis());
		obj.put(JSONConstants.JSON_EVENT_TYPE, type);

		return obj;
	}

	@Override
	public void close() throws IOException {
		eventPersister.persistToFile();
		repository.close();
	}

	public void recordCommitDescription(String text) {
		eventPersister.persistCommitDescription(text);

	}

	public int getTaskTimeOutInMinutes() {
		return taskTimeInMinutes;
	}
}
