package edu.oregonstate.cs589.comparator;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class Task {

	private Repository repository;
	private RevCommit targetCommit;
	private RevCommit parentCommit;

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
	
	public String getTargetCommitID(){
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
		System.err.println("Task start");
		
	}

	public void recordTaskEnd() {
		System.err.println("Task end");
	}

	public void recordDescriptionChange(String oldText, String newText) {
		System.err.println("Something got typed!");
		
	}
}
