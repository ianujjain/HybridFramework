package filesystem;

import java.io.File;

import org.apache.log4j.Logger;

import config.ApplicationMapping;

public class Directory {

	private static Directory instance;

	Logger Log = Logger.getLogger(Directory.class.getName());

	private Directory() {
		Log.info("Directory(): Initializing Instance");
	}

	public static Directory getInstance() {
		if (instance == null) {
			synchronized (ApplicationMapping.class) {
				if (instance == null) {
					instance = new Directory();
				}
			}
		}
		return instance;
	}

	public boolean createDirectory(final String dirPath) {
		boolean success = false;
		File directory = new File(dirPath);
		if (directory.exists()) {
			Log.warn("Directory already exists ...");
		} else {
			success = directory.mkdir();
			if (success) {
				Log.info("Successfully created new directory: " + dirPath);
			} else {
				Log.error("Failed to create new directory: " + dirPath);
			}
		}
		return success;
	}

	public boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		Log.info("removing file or directory : " + dir.getName());
		return dir.delete();
	}

}
