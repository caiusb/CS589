package edu.oregonstate.cs589.comparator;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob {

	public StartPluginUIJob(String string) {
		super(string);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		
		String compareVersion = null;
		String baseVersion = null;
		Repository repository = null;
		
		CompareUI.openCompareDialog(new GitCompareEditorInput(compareVersion, baseVersion, repository));
		
		return null;
	}
}
