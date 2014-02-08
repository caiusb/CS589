package edu.oregonstate.cs589.comparator;

import java.io.IOException;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class CommitTaskSpawner implements ViewSpawner {

	private String repoPath;
	private String commitID;

	public CommitTaskSpawner(String repoPath, String commitID) {
		this.repoPath = repoPath;
		this.commitID = commitID;
	}

	@Override
	public ManagedView spawnView() throws IOException, PartInitException {
		Task task = new Task(repoPath, commitID);

		String viewId = CommitView.ID;
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		IViewPart view = page.showView(viewId);
		CommitViewSetter viewSetter = (CommitViewSetter) view;
		viewSetter.setTask(task);

		return (ManagedView) view;
	}
}
