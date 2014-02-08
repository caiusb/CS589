package edu.oregonstate.cs589.comparator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Properties {

	private static final String PROPERTIES_FILENAME = "properties";
	private HashMap<String, String> properties = new HashMap<>();
	private Path propertyPath;

	public Properties() {
		propertyPath = Activator.getDefault().getLocalStoragePath().resolve(PROPERTIES_FILENAME);
		initFromFile();
	}

	private void initFromFile() {
		List<String> lines = readAllLines(propertyPath);

		for (String line : lines) {
			int separatorIndex = line.indexOf("=");
			String key = line.substring(0, separatorIndex);
			String value = line.substring(separatorIndex + 1);

			properties.put(key, value);
		}
	}

	private List<String> readAllLines(Path propertyPath) {
		List<String> readAllLines = null;

		try {
			readAllLines = Files.readAllLines(propertyPath, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return readAllLines;
	}

	public String getProperty(String key) {
		if (key == null)
			return null;
		
		return properties.get(key);
	}
}
