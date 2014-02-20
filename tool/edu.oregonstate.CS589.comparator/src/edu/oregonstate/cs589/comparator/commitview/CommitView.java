package edu.oregonstate.cs589.comparator.commitview;

import java.io.IOException;

import javax.security.auth.callback.LanguageCallback;

import org.eclipse.compare.CompareUI;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.oregonstate.cs589.comparator.FinishCallback;
import edu.oregonstate.cs589.comparator.ManagedView;
import edu.oregonstate.cs589.comparator.Task;

public class CommitView extends ViewPart implements CommitViewSetter,
		ManagedView {
	public static final String ID = "edu.oregonstate.CS589.comparator.commitView";

	private Task task;

	private Text commitMessage;
	private Text commitDescription;

	private FinishCallback finishCallback;

	public CommitView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite rootComposite = new Composite(parent, SWT.NONE);
		addGridLayout(rootComposite);

		addCommiMessage(rootComposite);

		addCommitDescription(rootComposite);

		addTimeOutInfo(rootComposite);

		addNextCommitButton(rootComposite);
	}

	private void addTimeOutInfo(Composite rootComposite) {
		final Display display = Display.getCurrent();
		final int step = 1;
		final int minutesTotal = 15;

		final Label timeOutLabel = new Label(rootComposite, SWT.BOLD);
		timeOutLabel.setText(minutesTotal + " minutes remaining");

		Font font = timeOutLabel.getFont();
		FontData[] fontData = font.getFontData();

		fontData[0].setHeight(20);
		fontData[0].setStyle(SWT.BOLD);

		timeOutLabel.setFont(new Font(display, fontData[0]));

		final Runnable timer = new Runnable() {
			
			int minutes = minutesTotal;
			
			public void run() {
				if (timeOutLabel.isDisposed())
					return;
				
				minutes -= step;
				
				timeOutLabel.setText(minutes + " minutes remaining");
				
				display.timerExec(1000, this);
			}
		};
		
		display.timerExec(step, timer);
	}

	private void addCommiMessage(Composite rootComposite) {
		Label commiMessageLabel = new Label(rootComposite, SWT.NONE);
		commiMessageLabel.setText("Commit message:");

		commitMessage = new Text(rootComposite, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		commitMessage.setEditable(false);
		addGridDataForText(commitMessage, 10);
	}

	private void addCommitDescription(Composite rootComposite) {
		Label commitDescriptionLabel = new Label(rootComposite, SWT.NONE);
		commitDescriptionLabel.setText("Your commit description:");

		commitDescription = new Text(rootComposite, SWT.V_SCROLL | SWT.MULTI);
		addGridDataForText(commitDescription, 15);

		commitDescription.addModifyListener(new ModifyListener() {

			String lastText = "";

			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				String newText = source.getText();

				task.recordDescriptionChange(lastText, newText);

				lastText = newText;
			}
		});
	}

	private void addGridDataForText(Text text, int lines) {
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = lines * text.getLineHeight();
		text.setLayoutData(gridData);
	}

	private void addNextCommitButton(Composite rootComposite) {
		Button nextCommitButton = new Button(rootComposite, SWT.PUSH);
		nextCommitButton.setText("Proceed to next commit");

		nextCommitButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				task.recordCommitDescription(commitDescription.getText());
				task.recordTaskEnd();
				close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private void addGridLayout(Composite element) {
		element.setLayout(new GridLayout(1, false));
		element.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
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

				GitCompareEditorInput compareEditorInput = new GitCompareEditorInput(task.getTargetCommitID(), task.getParentCommitID(), task.getRepository());
				CompareUI.openCompareEditor(compareEditorInput, true);
			}
		});

		task.recordTaskStart();
	}

	public void close() {
		try {
			task.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		page.closeAllEditors(false);
		page.hideView(this);

		finishCallback.iAmDone();
	}

	@Override
	public void addFinishCallback(FinishCallback callback) {
		this.finishCallback = callback;
	}
}
