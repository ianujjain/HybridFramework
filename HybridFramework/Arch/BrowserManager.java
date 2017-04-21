package base;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.remote.CapabilityType;

import helper.Constants;

public class BrowserManager {

	//private WebDriver driver = null;;
	private Logger Log = Logger.getLogger(BrowserManager.class.getName());
	private String command = null;

	//private static BrowserManager instance = new BrowserManager();

	//private BrowserManager() {
	//}

	//public static BrowserManager getInstance() {
		//return instance;
	//}

	public void initWebDriver() {
		try {
			if (Constants.DRIVER == null) {
				Log.info("Web Driver Initiate.");
				setDriver(Constants.BROWSER_TYPE, Constants.APP_URL);
				//return Constants.DRIVER;
			} else {
				//return Constants.DRIVER;
			}
		} catch (Exception e) {
			Log.error("BrowserLib : initWebDriver->" + e.getMessage());
			System.out.print(e.getMessage());
			//return null;
		}
	}

	public void getDriver() {
		try {
			if (Constants.DRIVER != null) {
				//return Constants.DRIVER;
			} else {
				Log.error("Web Driver does not Initiate.");
				//return null;
			}
		} catch (Exception e) {
			Log.error("BrowserLib : initWebDriver->" + e.getMessage());
			//return null;
		}
	}

	private void setDriver(final String browserType, final String appURL) throws IOException {
		switch (browserType) {
		case "chrome":
			Constants.DRIVER = initChromeDriver(appURL);
			command = "taskkill /F /IM chrome.exe /T";
			break;
		case "firefox":
			Constants.DRIVER = initFirefoxDriver(appURL);
			command = "taskkill /F /IM firefox.exe /T";
			break;
		case "InternetExplorer":
			Constants.DRIVER = initInternetExplorerDriver(appURL);
			command = "taskkill /F /IM iexplore.exe /T";
			break;
		case "opera":
			Constants.DRIVER = initOperaDriver(appURL);
			command = "taskkill /F /IM opera.exe /T";
			break;
		default:
			Log.info("Browser : " + browserType + " is invalid, Launching Firefox as browser of choice...");
			Constants.DRIVER = initFirefoxDriver(appURL);
			command = "taskkill /F /IM firefox.exe /T";
		}
	}

	private WebDriver initChromeDriver(final String appURL) throws IOException {
		try{
			Log.info("Launching Google-Chrome Browser...");
			
			File chromeFile = new File(Constants.CHROME_DRIVER);
			
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty("webdriver.chrome.driver", chromeFile.getAbsolutePath());

			WebDriver driver = new ChromeDriver();

			driver.manage().window().maximize();
			
			driver.navigate().to(appURL);

			return driver;
		}
		catch(Exception ex){
			Log.error("@@@@@@@@@@@@@@@@@@@@@@@@");
			return null;
		}
	}

	private WebDriver initInternetExplorerDriver(final String appURL) throws IOException {
		Log.info("Launching InternetExplorer Browser...");

		DesiredCapabilities objDesiredCapabilities = DesiredCapabilities.internetExplorer();
		objDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		System.setProperty("webdriver.ie.driver", Constants.INTERNET_EXPLORER_DRIVER);

		WebDriver driver = new InternetExplorerDriver();

		driver.manage().window().maximize();
		driver.navigate().to(appURL);

		return driver;
	}

	private WebDriver initFirefoxDriver(final String appURL) {
		Log.info("Launching Firefox browser..");

		ProfilesIni objProfilesIni = new ProfilesIni();
		FirefoxProfile objFirefoxProfile = objProfilesIni.getProfile(Constants.FIREFOX_PROFILE_NAME);
		objFirefoxProfile.setAssumeUntrustedCertificateIssuer(true);
		objFirefoxProfile.setAcceptUntrustedCertificates(true);

		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);

		return driver;
	}

	private WebDriver initOperaDriver(final String appURL) throws IOException {
		Log.info("Launching Opera Browser...");
		OperaOptions options = new OperaOptions();

		System.setProperty("webdriver.opera.driver", Constants.OPERA_DRIVER);
		options.setBinary(Constants.OPERA_BROWSER_LOCATION);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(OperaOptions.CAPABILITY, options);

		OperaDriver driver = new OperaDriver(capabilities);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);

		return driver;
	}

	public void CloseDrivers(WebDriver Webdriver) {
		if (Webdriver == null) {
			Log.error("The WebDriver browser instance was not initialized. You should first call the method InitBrowser.");
		}
		Webdriver.close();
		Webdriver.quit();
		//killProcess();
		Log.info("Web Driver Successfully Closed.");
	}

	private void killProcess() {
		try {
			Runtime.getRuntime().exec(command);
			Log.info("Command Execute: " + command);
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}

}