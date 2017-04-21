package report;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.MediaEntityBuilder;

import filesystem.Excel;
import helper.Constants;
import helper.ExtentManager;
import helper.MediaBuilder;
import helper.Utility;

public class Report {

	private static ExtentReports extent = ExtentManager.GetExtent();
	private static ExtentTest test;

	private static Excel objExcel = Excel.getInstance();
	private static Utility objUtility = Utility.getInstance();

	public static void extentCreateTest(final String name, final String description) {
		test = extent.createTest(name, description);
	}

	public static void logReort(final String strValue, final String status) throws IOException {
		
		String screenshotName = Constants.TEMP_MSG.replaceAll("\\s", "")+ "_" + objUtility.Timestamp() + ".png";
		
		System.out.println("Screen Shot Name: "+screenshotName);
		
		if (status.equalsIgnoreCase(Constants.PASS)) {
			objExcel.LogReport(Constants.CURRENT_SUIT, Constants.TEMP_MSG, strValue, Constants.PASS);
			
			MediaBuilder.captureScreenshot(Constants.DRIVER,screenshotName);
			MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(screenshotName).build();
			test.pass(Constants.TEMP_MSG, mediaModel);
		}
		if (status.equalsIgnoreCase(Constants.FAIL)) {
			objExcel.LogReport(Constants.CURRENT_SUIT, Constants.TEMP_MSG, strValue, Constants.FAIL);

			MediaBuilder.captureScreenshot(Constants.DRIVER,screenshotName);
			MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(screenshotName).build();
			test.fail(Constants.TEMP_MSG, mediaModel);

		}
		if (status.equalsIgnoreCase(Constants.SKIP)) {
			objExcel.LogReport(Constants.CURRENT_SUIT, Constants.TEMP_MSG, strValue, Constants.SKIP);
			MediaBuilder.captureScreenshot(Constants.DRIVER,screenshotName);
			MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(screenshotName).build();
			test.skip(Constants.TEMP_MSG, mediaModel);
		}
	}

	public static void logReort(final String SCName, final String strValue, final String status) throws IOException {
		if (status.equalsIgnoreCase(Constants.PASS)) {
			objExcel.LogReport(Constants.CURRENT_SUIT, SCName, strValue, Constants.PASS);
			test.pass(Constants.TEMP_MSG);
		}
		if (status.equalsIgnoreCase(Constants.FAIL)) {
			objExcel.LogReport(Constants.CURRENT_SUIT, SCName, strValue, Constants.FAIL);
			test.fail(Constants.TEMP_MSG);
		}
		if (status.equalsIgnoreCase(Constants.SKIP)) {
			objExcel.LogReport(Constants.CURRENT_SUIT, SCName, strValue, Constants.SKIP);
			test.skip(Constants.TEMP_MSG);
		}
	}

}