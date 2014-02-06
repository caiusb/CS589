package edu.oregonstate.cs589.comparator;

import java.io.IOException;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob {

	public StartPluginUIJob(String string) {
		super(string);
	}

	@SuppressWarnings({ "unused", "restriction"})
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		try {
			Task task = new Task("testData/repos/P/.git", "040d292f2ea983a918bd5be9d0242c5dcfff9f38");

			String viewId = CommitView.ID;
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
			IViewPart view = page.showView(viewId);
			CommitViewSetter viewSetter = (CommitViewSetter)view;
			viewSetter.setTask(task);
			
		} catch (PartInitException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void displayCommit() throws IOException {

		//CompareUI.openCompareDialog(new GitCompareEditorInput(task.getTargetCommitID(), task.getParentCommitID(), task.getRepository()));
	}
}
