package filesystem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.util.Enumeration;
import java.util.zip.ZipEntry;

import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import helper.Constants;

public class Zip {

	Logger Log = Logger.getLogger(Zip.class.getName());

	private ZipFile zipFile;

	/*
	 * Zip ObjZip = new Zip();
	 * 
	 * System.out.println("Trying to unzip file " + Constants.ZIP_FILE);
	 * 
	 * if
	 * (ObjZip.unzipToFile(Constants.ZIP_FILE,Constants.DESTINATION_DIRECTORY))
	 * { System.out.println("Succefully unzipped to the directory "+
	 * Constants.DESTINATION_DIRECTORY); } else { System.out.println(
	 * "There was some error during extracting archive to the directory "+
	 * Constants.DESTINATION_DIRECTORY); }
	 */

	public boolean unzip(String srcZipFileName, String destDirectoryName) {
		try {
			BufferedInputStream bufIS = null;
			File destDirectory = new File(destDirectoryName);
			destDirectory.mkdirs();

			File file = new File(srcZipFileName);
			zipFile = new ZipFile(file, ZipFile.OPEN_READ);

			Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries();

			while (zipFileEntries.hasMoreElements()) {
				
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				System.out.println("\tExtracting entry: " + entry);

				File destFile = new File(destDirectory, entry.getName());
				File parentDestFile = destFile.getParentFile();
				
				parentDestFile.mkdirs();

				if (!entry.isDirectory()) {
					
					bufIS = new BufferedInputStream(zipFile.getInputStream(entry));
					
					int currentByte;					
					byte data[] = new byte[Constants.BUFFER_SIZE];

					
					FileOutputStream fOS = new FileOutputStream(destFile);
					BufferedOutputStream bufOS = new BufferedOutputStream(fOS,Constants.BUFFER_SIZE);

					while ((currentByte = bufIS.read(data, 0,Constants.BUFFER_SIZE)) != -1) {
						bufOS.write(data, 0, currentByte);
					}

					bufOS.flush();
					bufOS.close();

					if (entry.getName().toLowerCase().endsWith(Constants.ZIP_EXTENSION)) {
						
						String zipFilePath = destDirectory.getPath()+ File.separatorChar + entry.getName();
						unzip(zipFilePath,zipFilePath.substring(0, zipFilePath.length() - Constants.ZIP_EXTENSION.length()));
					
					}
				}
			}
			bufIS.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
