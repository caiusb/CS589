package edu.oregonstate.cs589.comparator.infoview;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import edu.oregonstate.cs589.comparator.ManagedView;
import edu.oregonstate.cs589.comparator.ViewSpawner;

//TODO Code duplication with CommitViewSetter
public class InfoViewSpawner implements ViewSpawner {

	private String message;
	private String buttonMessage;

	public InfoViewSpawner(String message, String buttonMessage) {
		this.message = message;
		this.buttonMessage = buttonMessage;
	}

	@Override
	public ManagedView spawnView() throws Exception {
		String viewId = InfoView.ID;
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		IViewPart view = page.showView(viewId);
		InfoViewSetter viewSetter = (InfoViewSetter) view;
		viewSetter.setMessages(message, buttonMessage);

		return (ManagedView) view;
	}
}
