package base;

import org.apache.log4j.Logger;

import base.OperationBuilder;

import helper.Constants;
import helper.Utility;
import report.Report;

public class KeyWordBuilder {

	Logger Log = Logger.getLogger(KeyWordBuilder.class.getName());
	OperationBuilder objOperationBuilder = OperationBuilder.getInstance();
	private static KeyWordBuilder instance = null;

	private Utility objUtility = Utility.getInstance();

	public static KeyWordBuilder getInstance() {
		if (instance == null) {
			instance = new KeyWordBuilder();
		}
		return instance;
	}

	public void Operation(final String action, String ObjectXpathAlias, final String ObjectValue, final String strValue)
			throws Exception {
		int min, max;
		String[] arrdata = null;

		switch (action) {

		case "OPEN":
			objOperationBuilder.login();
			break;
		case "CLOSE":
			objOperationBuilder.close();
			break;
		case "QUIT":
			objOperationBuilder.quit();
			break;
		case "NOTHING":
			break;
		case "REPORT":
			if (ObjectXpathAlias.equalsIgnoreCase("NOTHING")) 
			{
				Report.logReort(strValue, Constants.PASS);
			} 
			else 
			{
				objOperationBuilder.report(strValue, ObjectXpathAlias);
			}
			break;
		case "WAIT":
			int microseconds = Integer.parseInt(ObjectValue);
			objOperationBuilder.wait(microseconds);
			break;
		case "SYNC":
			objOperationBuilder.syncElement(ObjectXpathAlias);
			break;
		case "CLICK": 
			objOperationBuilder.click(ObjectXpathAlias);
			break;
		case "MOUSEOVER": 
			objOperationBuilder.click(ObjectXpathAlias);
			break;
		case "KEYIN":
			objOperationBuilder.enterText(ObjectXpathAlias, strValue);
			break;
		case "KEYINRANDTEXT":
			arrdata = strValue.split(Constants.DELIMA_HYPHEN);

			min = Integer.parseInt(arrdata[0]);
			max = Integer.parseInt(arrdata[1]);

			String randData = objUtility.getRandomString(min, max);
			// LocalStorage.USR_NAME = randData;
		
			Report.logReort(Constants.TEMP_MSG+": "+ randData, Constants.PASS);
			objOperationBuilder.enterText(ObjectXpathAlias, randData);
			break;
		case "KEYINRANDEMAIL":
			break;
		case "KEYINRANDNUM":
			break;
		// BEGIN: Drop Down Key Words:
		case "LISTITEM":
			objOperationBuilder.selectByVisibleText(ObjectXpathAlias, strValue);
			break;
		case "SELECTRANDITEM":
			arrdata = strValue.split(Constants.DELIMA_HYPHEN);

			min = Integer.parseInt(arrdata[0]);
			max = Integer.parseInt(arrdata[1]);

			int index = objUtility.getRandomNumber(min, max);
			Report.logReort(Constants.TEMP_MSG+": "+ index, Constants.PASS);
			objOperationBuilder.selectByIndex(ObjectXpathAlias, index);
			break;
		// END: Drop Down Key Words:
		case "EXPORT":
			break;

		case "TYPE":
			break;
		case "TYPEINT":
			break;
		case "TYPESTR":
			break;
		case "TYPEDBL":
			break;
		case "TYPELNG":
			break;
		// Date
		case "TYPEDATE":
			switch (action) {
			case "TODAY":
				break;
			case "YESTERDAY":
				break;
			case "TOMORROW":
				break;
			}
			break;
		case "TYPEEMAIL":
			break;

		default:
		}
	}

}