package helper;

import org.openqa.selenium.WebDriver;

public class Constants {

	// Temp Msg OR Storage

	public static final String USERNAME = "russia4";
	public static final String AUTOMATE_KEY = "jfg8yUzzzzyzMaVajJqL";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	public static String TEMP_MSG = "";

	public static final String EXTENT_REPORT_FILE_PATH = "./Report/ExtentReport/extentreport.html";
	public static final String SCREENS_PATH = System.getProperty("user.dir") + "\\Report\\ExtentReport\\";
	
	

	public static final String REPORT_TITLE = "Orange HRM  Automation Report";
	// public static final String REPORT_NAME = "Full Regression Cycle";

	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String SKIP = "SKIP";

	public static WebDriver DRIVER = null;
	public static final String DELIMA_COMMA = "\\.";
	public static final String DELIMA_COLON = "\\:";
	public static final String DELIMA_HYPHEN = "\\-";

	public static final String CHARLIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public final static String BROWSER_TYPE = "chrome";
	// public final static String BROWSER_TYPE = "InternetExplorer";
	// public final static String APP_URL = "https://www.google.com";

	// public final static String APP_URL =
	// "http://opensource.demo.orangehrmlive.com/";
	public final static String APP_URL = "https://enterprise-demo.orangehrmlive.com/";

	public static final String FIREFOX_DRIVER = "D:/Edu-Software/Selenium/DriverDriver";
	public static final String CHROME_DRIVER = "D:/Edu-Software/Selenium/Driver/chromedriver.exe";
	public static final String OPERA_DRIVER = "D:/Edu-Software/Selenium/Driver/operadriver.exe";
	public static final String SAFARI_DRIVER = "D:/Edu-Software/Selenium/DriverDriver";
	public static final String INTERNET_EXPLORER_DRIVER = "D:/Edu-Software/Selenium/Driver/IEDriverServer.exe";
	public static final String OPERA_BROWSER_LOCATION = "C:\\Program Files (x86)\\Opera\\launcher.exe";
	public static final String FIREFOX_PROFILE_NAME = "profileToolsQA";

	public static final String OBJ_REPO_PATH = System.getProperty("user.dir") + "\\InputFile\\.xlsx";
	public static final String TEST_RESULT_DIR = System.getProperty("user.dir") + "\\TestResult\\";
	public static final String TEST_RESULT_FILE_NAME = "TestResult.xlsx";
	public static String TEST_RESULT_PATH = null;
	public final static String SUIT_TO_RUN = "ADMINREG";
	public static String CURRENT_SUIT = null;

	public static final String TESTNG_DEFAULT_OUTPUTDIR = System.getProperty("user.dir") + "\\Report\\";

	// public final static String SUIT_TO_RUN = "ADMINREG.Anuj.Jain";

	public static final String UNZIP_LOCATION = System.getProperty("user.dir") + "\\UnZipFile\\";
	public static final String ZIP_LOCATION = System.getProperty("user.dir") + "\\ZipFile\\";
	public final static int BUFFER_SIZE = 2048;
	public final static String ZIP_FILE = "D:\\Test\\jain.zip";
	public final static String DESTINATION_DIRECTORY = System.getProperty("user.dir") + "\\UnZipFile\\";
	public final static String ZIP_EXTENSION = ".zip";
	public static final String TEMP_FILES = System.getProperty("user.dir") + "\\TempFile\\";

	public static final String KILL_PROCESS = System.getProperty("user.dir") + "\\Scripts\\KillBrowser.bat";

	public static String IGN_NEXT_EXE_COND = "N";

}
