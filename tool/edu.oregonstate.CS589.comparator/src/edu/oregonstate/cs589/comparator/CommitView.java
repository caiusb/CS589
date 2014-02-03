package edu.oregonstate.cs589.comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class CommitView extends ViewPart {
	public static final String ID = "edu.oregonstate.CS589.comparator.commitView";

	public CommitView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		RowLayout layout = new RowLayout();
		parent.setLayout(layout);
		
		Label label = new Label(parent, SWT.NONE);
		label.setText("Hello World");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
