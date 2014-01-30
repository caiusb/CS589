package edu.oregonstate.cs589.comparator;

import java.io.File;
import java.io.IOException;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob {

	public StartPluginUIJob(String string) {
		super(string);
	}

	@SuppressWarnings({ "unused", "restriction" })
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {

		try {
			File repoFile = Activator.getDefault().getProjectFile("testData/P/.git");
			Repository repository = new FileRepository(repoFile);

			RevWalk rw = new RevWalk(repository);	
			System.out.println(repository.getAllRefs());
			
			ObjectId cmitId = repository.resolve("040d292f2ea983a918bd5be9d0242c5dcfff9f38");
			
			RevCommit compareCommit = rw.parseCommit(repository.resolve("040d292f2ea983a918bd5be9d0242c5dcfff9f38"));
			RevCommit baseCommit = rw.parseCommit(repository.resolve("2a5d7c13205ffb27cb9dca9875ef1261acba808b"));
			
			CompareUI.openCompareDialog(new GitCompareEditorInput(compareCommit.getName(), baseCommit.getName(), repository));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
