package edu.oregonstate.cs589.comparator;

import org.eclipse.compare.CompareUI;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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

	private Composite rootComposite;
	private Text commitMessage;
	private Text commitDescription;

	public CommitView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		rootComposite = new Composite(parent, SWT.NONE);
		addGridLayout(rootComposite);
		
		Label commiMessageLabel = new Label(rootComposite, SWT.NONE);
		commiMessageLabel.setText("Commit message:");

		commitMessage = new Text(rootComposite, SWT.BORDER);
		commitMessage.setEditable(false);
		
		Label commitDescriptionLabel = new Label(rootComposite, SWT.NONE);
		commitDescriptionLabel.setText("Your commit description:");

		commitDescription = new Text(rootComposite, SWT.V_SCROLL | SWT.MULTI);
	    
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = 15 * commitDescription.getLineHeight();
		commitDescription.setLayoutData(gridData);
	    
		Button nextCommitButton = new Button(rootComposite, SWT.PUSH);
		nextCommitButton.setText("Proceed to next commit");
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
	}

}
