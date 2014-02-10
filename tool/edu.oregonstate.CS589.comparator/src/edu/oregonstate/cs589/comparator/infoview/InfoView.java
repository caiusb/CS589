package edu.oregonstate.cs589.comparator.infoview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class InfoView extends ViewPart implements InfoViewSetter {
	public static final String viewID = "edu.oregonstate.CS589.comparator.infoView";
	
	private Label infoLabel;
	private Button button;

	public InfoView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite rootComposite = new Composite(parent, SWT.NONE);
		rootComposite.setLayout(new RowLayout(SWT.VERTICAL));

		infoLabel = new Label(rootComposite, SWT.BORDER);
		button = new Button(rootComposite, SWT.PUSH);
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
				infoLabel.setText(message);
				infoLabel.pack(true);
			}
		});
	}

	@Override
	public void setButtonMessage(final String buttonMessage) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				button.setText(buttonMessage);
				button.pack(true);
			}
		});
	}
}
