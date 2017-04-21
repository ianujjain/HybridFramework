package base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import helper.Constants;
import helper.Utility;
import report.Report;

public class OperationBuilder {

	private Logger Log = Logger.getLogger(OperationBuilder.class.getName());

	private static OperationBuilder instance = new OperationBuilder();
	private ObjectBuilder objObjectBuilder = ObjectBuilder.getInstance();
	private WebDriver driver = null;
	private Utility objUtility = Utility.getInstance();

	private OperationBuilder() {
	}

	public static OperationBuilder getInstance() {
		return instance;
	}

	private void initWebDriver() throws MalformedURLException {
		switch (Constants.BROWSER_TYPE) {
		case "chrome":
			Log.info("Launching Google-Chrome Browser.");

			File chromeFile = new File(Constants.CHROME_DRIVER);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty("webdriver.chrome.driver", chromeFile.getAbsolutePath());

			driver = new ChromeDriver();
			Constants.DRIVER = driver;
			//driver.close();
			//driver.manage().window().maximize();
			driver.navigate().to(Constants.APP_URL);

			break;
		case "firefox":
			Log.info("Launching Firefox browser..");

			ProfilesIni objProfilesIni = new ProfilesIni();
			FirefoxProfile objFirefoxProfile = objProfilesIni.getProfile(Constants.FIREFOX_PROFILE_NAME);
			objFirefoxProfile.setAssumeUntrustedCertificateIssuer(true);
			objFirefoxProfile.setAcceptUntrustedCertificates(true);

			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.navigate().to(Constants.APP_URL);

			break;
		case "InternetExplorer":
			Log.info("Launching InternetExplorer Browser.");

			DesiredCapabilities objDesiredCapabilities = DesiredCapabilities.internetExplorer();
			objDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			System.setProperty("webdriver.ie.driver", Constants.INTERNET_EXPLORER_DRIVER);

			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			driver.navigate().to(Constants.APP_URL);

			break;
		case "opera":
			Log.info("Launching Opera Browser.");
			OperaOptions options = new OperaOptions();

			System.setProperty("webdriver.opera.driver", Constants.OPERA_DRIVER);
			options.setBinary(Constants.OPERA_BROWSER_LOCATION);

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(OperaOptions.CAPABILITY, options);

			driver = new OperaDriver(capabilities);
			driver.manage().window().maximize();
			driver.navigate().to(Constants.APP_URL);

			break;
		case "Cross":
			
			DesiredCapabilities caps = new DesiredCapabilities();
			   
		    caps.setPlatform(Platform.MAC);
		    caps.setCapability("browserstack.debug", "true");

		    driver = new RemoteWebDriver(new URL(Constants.URL), caps);
		    driver.navigate().to(Constants.APP_URL);			
			break;
		default:
			Log.info("Default Launching Google-Chrome Browser.");

			File dchromeFile = new File(Constants.CHROME_DRIVER);

			DesiredCapabilities dcap = DesiredCapabilities.chrome();
			dcap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty("webdriver.chrome.driver", dchromeFile.getAbsolutePath());

			driver = new ChromeDriver();
			//driver.manage().window().maximize();
			driver.navigate().to(Constants.APP_URL);

		}
	}

	public void login() {
		try {
			initWebDriver();
		} catch (Exception objException) {
			Log.error("OperationBuilder | openApp(): Unable to Open Application OR Broeser:");
		}
	}

	public void close() {
		if (this.driver == null) {
			Log.error(
					"The WebDriver browser instance was not initialized. You should first call the method InitBrowser.");
		}
		this.driver.close();
		Log.info("Browser Closed sSuccessfully .");
	}

	public void quit() {
		try {
			if (!Constants.DRIVER.equals(null)) {
				this.driver.close();
				this.driver.quit();
				objUtility.killProcess();
				Log.info("Web Driver Successfully Closed.");
			}
		} catch (Exception e) {
			Log.error(
					"The WebDriver browser instance was not initialized. You should first call the method InitBrowser.");
		}
	}

	public void report(String strValue, String ObjectXpathAlias) throws IOException {
		WebElement element = objObjectBuilder.getElement(ObjectXpathAlias);
		try {
			if (!element.equals(null)) {
				Log.info("Webelement " + ObjectXpathAlias);
				Report.logReort(strValue, Constants.PASS);
			} else {
				Report.logReort(strValue, Constants.FAIL);
			}
		} catch (Exception e) {
			Report.logReort(strValue, Constants.FAIL);
		}
	}

	public void click(String ObjectXpathAlias) {
		try {
			WebElement element = objObjectBuilder.getElement(ObjectXpathAlias);
			Log.info("Clicking on Webelement " + ObjectXpathAlias);
			element.click();
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage() + "ObjectXpathAlias:  " + ObjectXpathAlias);
		}
	}

	public void enterText(String ObjectXpathAlias, String data) {
		try {
			WebElement element = objObjectBuilder.getElement(ObjectXpathAlias);
			Log.info("Clicking on Webelement " + ObjectXpathAlias + "Entered Data is: " + data);
			element.clear();
			element.sendKeys(data);
		} catch (Exception e) {
			Log.error("Not able to Enter Text --- " + e.getMessage());
		}
	}

	public void selectByVisibleText(String ObjectXpathAlias, String data) {
		try {
			WebElement element = objObjectBuilder.getElement(ObjectXpathAlias);
			Log.info("Clicking on Webelement " + ObjectXpathAlias);
			Select selectElement = new Select(element);
			Log.info("Select Element: " + data);
			selectElement.selectByVisibleText(data);
		} catch (Exception e) {
			Log.error("Not Item Selected --- " + e.getMessage());
		}
	}

	public void selectByIndex(String ObjectXpathAlias, int index) {
		try {
			WebElement element = objObjectBuilder.getElement(ObjectXpathAlias);
			Log.info("Clicking on Webelement " + ObjectXpathAlias);
			Select selectElement = new Select(element);
			Log.info("Select Element By Index: " + index);
			selectElement.selectByIndex(index);
		} catch (Exception e) {

			Log.error("Not Item Selected --- " + e.getMessage());
		}
	}

	public void syncElement(String ObjectXpathAlias) {
		try {
			WebElement element = objObjectBuilder.fluentWait(ObjectXpathAlias);
			Log.info("Synchronizing...: " + ObjectXpathAlias);
			if (!element.equals(null)) {
				Log.info("Synchronization Done For:" + ObjectXpathAlias);
			} else {
				Log.error("Synchronization Is Not Done For:" + ObjectXpathAlias);
			}
		} catch (Exception e) {

			Log.error(" Exception: " + e.getMessage());
		}

	}

	public void wait(int microseconds) throws Exception {
		try {
			Log.info("Wait for " + microseconds + " seconds.");
			Thread.sleep(microseconds);
		} catch (Exception e) {

			Log.error("Not able to Wait --- " + e.getMessage());
		}
	}
	
	public void mouseOver(String ObjectXpathAlias) {
		try {
			Actions actions = new Actions(Constants.DRIVER);
			WebElement element = objObjectBuilder.getElement(ObjectXpathAlias);
			actions.moveToElement(element);
			Log.info("Move Mouse on Webelement " + ObjectXpathAlias);
			element.click();
		} catch (Exception e) {
			Log.error("Not able to Move Mouse --- " + e.getMessage() + "ObjectXpathAlias:  " + ObjectXpathAlias);
		}
	}
}