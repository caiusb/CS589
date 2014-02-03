package edu.oregonstate.cs589.comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class CommitView extends ViewPart implements CommitViewSetter{
	public static final String ID = "edu.oregonstate.CS589.comparator.commitView";
	
	private Label label;
	private Composite parent;

	public CommitView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		RowLayout layout = new RowLayout();
		parent.setLayout(layout);
		
		label = new Label(parent, SWT.NONE);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMessage(final String message) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				label.setText(message);
				label.pack(true);
			}
		});
	}

}
