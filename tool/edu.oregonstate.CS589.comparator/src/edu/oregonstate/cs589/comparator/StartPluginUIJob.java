package edu.oregonstate.cs589.comparator;

import java.io.File;
import java.io.IOException;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob {

	public StartPluginUIJob(String string) {
		super(string);
	}

	@SuppressWarnings({ "unused", "restriction" })
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		Repository repository = null;
		

		try {
			File repoFile = Activator.getDefault().getProjectFile("testData/P");
			repository = new FileRepository(repoFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		String compareVersion = null;
		String baseVersion = null;
		
		CompareUI.openCompareDialog(new GitCompareEditorInput(compareVersion, baseVersion, repository));
		
		return null;
	}
}
