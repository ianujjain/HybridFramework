package base;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;

import helper.Constants;
import report.Report;

public class ObjectBuilder {

	Logger Log = Logger.getLogger(ObjectBuilder.class.getName());
	private static ObjectBuilder instance;

	private ObjectBuilder() {
	}

	public static ObjectBuilder getInstance() {
		if (instance == null) {
			instance = new ObjectBuilder();
		}
		return instance;
	}

	private By getObject(final String strFindBy, final String strSelectorValue) {

		By by = null;
		try {
			switch (strFindBy) {
			case "className":
				by = By.className(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "cssSelector":
				by = By.cssSelector(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "id":
				by = By.id(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "linkText":
				by = By.linkText(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "name":
				by = By.name(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "partialLinkText":
				by = By.partialLinkText(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "tagName":
				by = By.tagName(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "xpath":
				by = By.xpath(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			case "javaScript":
				by = By.xpath(strSelectorValue);
				Log.info("Find Element By " + strFindBy + " and Find Element By Value: " + strSelectorValue);
				break;
			default:
				by = null;
				Log.error("Find Element By : " + strFindBy + " Is Invalid.");
				break;
			}
			return by;

		} catch (Exception e) {
			Constants.IGN_NEXT_EXE_COND = "Y";
			return by;
		}

	}

	public WebElement getElement(String ObjectXpathAlias) {
		By by = null;
		WebElement element = null;

		String strFindBy, strSelectorValue;

		String[] arrObjectXpathAlias = ObjectXpathAlias.split(Constants.DELIMA_COLON);
		strFindBy = arrObjectXpathAlias[0];
		strSelectorValue = arrObjectXpathAlias[1];

		try {
			by = getObject(strFindBy, strSelectorValue);
			element = Constants.DRIVER.findElement(by);
			return element;

		} catch (NoSuchElementException objException) {
			Constants.IGN_NEXT_EXE_COND = "Y";
			Log.error("No Such Element Found, Find Element By " + strFindBy + " and Find Element By Value: "+ "and Set IGN_NEXT_EXE_COND: Y");
			return element;
		}
	}

	public List<WebElement> getElements(String ObjectXpathAlias) {
		By by = null;
		List<WebElement> elementList = null;

		String strFindBy, strSelectorValue;
		strFindBy = ObjectXpathAlias.split(Constants.DELIMA_COLON)[0];
		strSelectorValue = ObjectXpathAlias.split(Constants.DELIMA_COLON)[1];

		try {
			by = getObject(strFindBy, strSelectorValue);
			elementList = Constants.DRIVER.findElements(by);
			return elementList;

		} catch (Exception objException) {
			Constants.IGN_NEXT_EXE_COND = "Y";
			Log.error("No Such Element Found, Find Element By " + strFindBy + " and Find Element By Value: "
					+ strSelectorValue + "and Set IGN_NEXT_EXE_COND: Y");
			return elementList;
		}
	}

	public WebElement getElementByJS(String ObjectXpathAlias) {
		WebElement element = null;

		String strFindBy, strSelectorValue;
		strFindBy = ObjectXpathAlias.split(Constants.DELIMA_COLON)[0];
		strSelectorValue = ObjectXpathAlias.split(Constants.DELIMA_COLON)[1];

		try {
			if (strFindBy == "javaScript") {
				JavascriptExecutor js = (JavascriptExecutor) Constants.DRIVER;
				element = (WebElement) js.executeScript(strSelectorValue);
			}
			return element;
		} catch (Exception objException) {
			Constants.IGN_NEXT_EXE_COND = "Y";
			Log.error("No Such Element Found, Find Element By " + strFindBy + " and Find Element By Value: "
					+ strSelectorValue + "and Set IGN_NEXT_EXE_COND: Y");
			return element;
		}
	}

	@SuppressWarnings("unchecked")
	public List<WebElement> getElementsByJS(String ObjectXpathAlias) {
		List<WebElement> elementList = null;

		String strFindBy, strSelectorValue;
		strFindBy = ObjectXpathAlias.split(Constants.DELIMA_COLON)[0];
		strSelectorValue = ObjectXpathAlias.split(Constants.DELIMA_COLON)[1];

		try {
			if (strFindBy == "javaScript") {
				JavascriptExecutor js = (JavascriptExecutor) Constants.DRIVER;
				elementList = (List<WebElement>) js.executeScript(strSelectorValue);
			}
			return elementList;
		} catch (Exception objException) {
			Constants.IGN_NEXT_EXE_COND = "Y";
			Log.error("No Such Element Found, Find Element By " + strFindBy + " and Find Element By Value: "
					+ strSelectorValue + "and Set IGN_NEXT_EXE_COND: Y");
			return elementList;
		}
	}

	public WebElement fluentWait(final String ObjectXpathAlias) throws IOException {
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Constants.DRIVER);

			wait.withTimeout(60, TimeUnit.SECONDS);
			wait.pollingEvery(10, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class);

			WebElement sync = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {

					By by = null;

					String strFindBy, strSelectorValue;
					String[] arrObjectXpathAlias = ObjectXpathAlias.split(Constants.DELIMA_COLON);
					strFindBy = arrObjectXpathAlias[0];
					strSelectorValue = arrObjectXpathAlias[1];

					by = getObject(strFindBy, strSelectorValue);

					return driver.findElement(by);
				}
			});
			return sync;
		} catch (Exception e) {
			Constants.IGN_NEXT_EXE_COND = "Y";
			Report.logReort(Constants.TEMP_MSG ,Constants.FAIL);
			return null;
		}
	}

}