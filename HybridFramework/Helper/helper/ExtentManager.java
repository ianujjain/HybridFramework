package helper;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
	//private static String filePath = "./Report/ExtentReport/extentreport.html";

	public static ExtentReports GetExtent() {
		if (extent != null)
			return extent; 
		extent = new ExtentReports();
		extent.attachReporter(getHtmlReporter());
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {
		htmlReporter = new ExtentHtmlReporter(Constants.EXTENT_REPORT_FILE_PATH);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle( "Orange HRM  Automation Report");
		htmlReporter.config().setReportName("ADMINREG");
			
		return htmlReporter;
	}

	public static ExtentTest createTest(String name, String description) {
		test = extent.createTest(name, description);
		return test;
	}

}