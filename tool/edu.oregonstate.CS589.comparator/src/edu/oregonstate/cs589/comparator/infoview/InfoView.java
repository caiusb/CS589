package edu.oregonstate.cs589.comparator.infoview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.oregonstate.cs589.comparator.FinishCallback;
import edu.oregonstate.cs589.comparator.ManagedView;

//TODO Code duplication with CommitView. Extract common Template Class.
public class InfoView extends ViewPart implements InfoViewSetter, ManagedView {
	public static final String ID = "edu.oregonstate.CS589.comparator.infoView";

	private Label infoLabel;
	private Button button;

	private FinishCallback finishCallback;

	public InfoView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite rootComposite = new Composite(parent, SWT.NONE);
		rootComposite.setLayout(new RowLayout(SWT.VERTICAL));

		infoLabel = new Label(rootComposite, SWT.BORDER);
		button = new Button(rootComposite, SWT.PUSH);

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);

			}
		});
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void setMessages(final String message, final String buttonMessage) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				infoLabel.setText(message);
				infoLabel.pack(true);

				button.setText(buttonMessage);
				button.pack(true);
			}
		});
	}

	@Override
	public void addFinishCallback(FinishCallback callback) {
		this.finishCallback = callback;
	}

	private void close() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		page.hideView(this);

		finishCallback.iAmDone();
	}
}
