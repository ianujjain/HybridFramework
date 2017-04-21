package helper;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class MediaBuilder {

	private static Logger Log = Logger.getLogger(MediaBuilder.class.getName());

	public static void captureScreenshot(WebDriver driver, String screenshotName) {
		try {

			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			String strDestinationPath = Constants.SCREENS_PATH + screenshotName;

			File destination = new File(strDestinationPath);
			FileUtils.copyFile(source, destination);

		} catch (Exception e) {
			Log.error("Exception while taking screenshot " + e.getMessage());
		}
	}

}
