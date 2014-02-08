package edu.oregonstate.cs589.comparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;

public class StartPluginUIJob extends UIJob implements FinishCallback{

	private List<ViewSpawner> viewSpawners;
	private Iterator<ViewSpawner> viewSpawnerIterator;

	public StartPluginUIJob(String string) {
		super(string);
		viewSpawners = new ArrayList<>();
	}

	@SuppressWarnings({ "unused", "restriction"})
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		
		viewSpawners.add(new CommitTaskSpawner("testData/repos/P/.git", "040d292f2ea983a918bd5be9d0242c5dcfff9f38"));
		viewSpawners.add(new CommitTaskSpawner("testData/repos/P/.git", "1dfed3f41204035f0e6ec29ccf69d55a44274e35"));
		viewSpawnerIterator = viewSpawners.iterator();
		
		spawnNextView();
		
		return Status.OK_STATUS;
	}

	private void spawnNextView() {
		try {
			
			if (viewSpawnerIterator.hasNext()){
				ViewSpawner viewSpawner = viewSpawnerIterator.next();
				ManagedView view = viewSpawner.spawnView();
				view.addFinishCallback(this);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void iAmDone() {
		spawnNextView();
	}
}
