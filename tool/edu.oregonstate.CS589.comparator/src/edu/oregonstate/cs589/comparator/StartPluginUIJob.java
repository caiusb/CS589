package edu.oregonstate.cs589.comparator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob {

	private Activator activator;

	public StartPluginUIJob(String string) {
		super(string);
		this.activator = activator;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		try {
			String viewId = CommitView.ID;
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
			IViewPart view = page.showView(viewId);
			CommitViewSetter viewSetter = (CommitViewSetter)view;
			viewSetter.setMessage("Hello World");
			
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
