package edu.oregonstate.cs589.comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class CommitView extends ViewPart implements CommitViewSetter{
	public static final String ID = "edu.oregonstate.CS589.comparator.commitView";
	
	private Task task;

	private Composite parent;
	private Text commitMessage;

	public CommitView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		parent.setLayout(layout);
		
		Label commiMessageLabel = new Label(parent, SWT.NONE);
		commiMessageLabel.setText("Commit message:");
		
		commitMessage = new Text(parent, SWT.BORDER);
		commitMessage.setEditable(false);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTask(final Task task) {
		this.task = task;
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				commitMessage.setText(task.getCommitMessage());
				commitMessage.pack(true);
			}
		});
	}

}
