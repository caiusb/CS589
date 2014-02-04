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

	@SuppressWarnings({ "unused", "restriction"})
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {

		try {
			displayCommit();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private void displayCommit() throws IOException {
		Task task = new Task("testData/P/.git", "040d292f2ea983a918bd5be9d0242c5dcfff9f38");

		CompareUI.openCompareDialog(new GitCompareEditorInput(task.getTargetCommitID(), task.getParentCommitID(), task.getRepository()));
	}
}
