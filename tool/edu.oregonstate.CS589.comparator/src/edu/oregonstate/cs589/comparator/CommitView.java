package edu.oregonstate.cs589.comparator;

import org.eclipse.compare.CompareUI;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class CommitView extends ViewPart implements CommitViewSetter{
	public static final String ID = "edu.oregonstate.CS589.comparator.commitView";
	
	private Task task;

	private Text commitMessage;
	private Text commitDescription;

	public CommitView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite rootComposite = new Composite(parent, SWT.NONE);
		addGridLayout(rootComposite);
		
		addCommiMessage(rootComposite);
		
		addCommitDescription(rootComposite);
	    
		addNextCommitButton(rootComposite);
	}

	private void addCommiMessage(Composite rootComposite) {
		Label commiMessageLabel = new Label(rootComposite, SWT.NONE);
		commiMessageLabel.setText("Commit message:");

		commitMessage = new Text(rootComposite, SWT.BORDER);
		commitMessage.setEditable(false);
	}

	private void addCommitDescription(Composite rootComposite) {
		Label commitDescriptionLabel = new Label(rootComposite, SWT.NONE);
		commitDescriptionLabel.setText("Your commit description:");

		commitDescription = new Text(rootComposite, SWT.V_SCROLL | SWT.MULTI);
	    
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = 15 * commitDescription.getLineHeight();
		commitDescription.setLayoutData(gridData);
		
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

	private void addNextCommitButton(Composite rootComposite) {
		Button nextCommitButton = new Button(rootComposite, SWT.PUSH);
		nextCommitButton.setText("Proceed to next commit");
		
		nextCommitButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				task.recordTaskEnd();
				
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
}
