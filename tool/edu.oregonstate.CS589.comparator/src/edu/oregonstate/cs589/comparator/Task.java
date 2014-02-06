package edu.oregonstate.cs589.comparator;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class Task {

	private String repoPath;
	private String targetCommitID;
	private String parentCommitID;
	private Repository repository;

	public Task(String repoPath, String targetCommitID) throws IOException {
		this.repoPath = repoPath;
		this.targetCommitID = targetCommitID;
		
		File repoFile = Activator.getDefault().getProjectFile("testData/P/.git");
		repository = new FileRepository(repoFile);

		RevWalk rw = new RevWalk(repository);	
		System.out.println(repository.getAllRefs());
		
		RevCommit targetCommit = rw.parseCommit(repository.resolve(targetCommitID));
		
		RevCommit[] parents = targetCommit.getParents();
		
		if (parents.length != 1)
			throw new RuntimeException("Commit must have only one parent!");
		
		parentCommitID = parents[0].getName();
	}
	
	public String getTargetCommitID(){
		return targetCommitID;
	}
	
	public String getParentCommitID() {
		return parentCommitID;
	}

	public Repository getRepository() {
		return repository;
	}
}
