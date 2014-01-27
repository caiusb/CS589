package edu.oregonstate.cs589.comparator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob {

	private Activator activator;

	public StartPluginUIJob(String string) {
		super(string);
		this.activator = activator;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "title", "hello world");
		return null;
	}
}
