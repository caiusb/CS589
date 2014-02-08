package edu.oregonstate.cs589.comparator;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.json.simple.JSONObject;

public class Task {

	private Repository repository;
	private RevCommit targetCommit;
	private RevCommit parentCommit;

	private String userID = "Ion";
	private String taskID = "T01";
	private String commitOrigin = "Git";

	public Task(String repoPath, String targetCommitID) throws IOException {
		File repoFile = Activator.getDefault().getProjectFile(repoPath);
		repository = new FileRepository(repoFile);

		RevWalk rw = new RevWalk(repository);
		System.out.println(repository.getAllRefs());

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

		EventPersister.persist(obj);
	}

	public void recordTaskEnd() {
		JSONObject obj = createCommonJSON(JSONConstants.EVENT_TASK_END);

		EventPersister.persist(obj);
	}

	public void recordDescriptionChange(String oldText, String newText) {
		JSONObject obj = createCommonJSON(JSONConstants.EVENT_TYPE);

		EventPersister.persist(obj);
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
}
