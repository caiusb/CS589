package edu.oregonstate.cs589.comparator.rcp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "edu.oregonstate.CS589.comparator"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public File getProjectFile(String filePath) {
		File repo = null;

		try {
			URL bundleRoot = getBundle().getEntry("/");
			URL fileURL = FileLocator.toFileURL(bundleRoot);
			File bundleFile = new File(fileURL.toURI().getRawPath());

			log("bundle file: " + bundleFile.getAbsolutePath());

			repo = new File(bundleFile, filePath);

			log("repo file: " + repo.getAbsolutePath());

			log("repo exists: " + repo.exists());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		return repo;
	}

	public java.nio.file.Path getLocalStoragePath() {
		return getStateLocation().toFile().toPath();
	}

	public void log(Exception e) {
		getLog().log(new Status(0, PLUGIN_ID, e.getMessage(), e));
	}

	public void log(String message) {
		getLog().log(new Status(0, PLUGIN_ID, message));
	}
}
