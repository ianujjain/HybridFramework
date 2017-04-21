package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import config.ApplicationMapping;
import filesystem.Directory;
import filesystem.Excel;
import helper.Constants;
import helper.ExtentManager;
import helper.Utility;
import report.Report;
import base.KeyWordBuilder;

public class Main {

	ApplicationMapping objAppMap = ApplicationMapping.getInstance();
	KeyWordBuilder objKeyWordBuilder = KeyWordBuilder.getInstance();

	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;

	HashMap<String, String> objRepo = null;
	Utility objUtility = Utility.getInstance();
	Directory objDir = Directory.getInstance();
	Excel objExcel = Excel.getInstance();

	private Logger Log = Logger.getLogger(Main.class.getName());

	@BeforeClass
	void initializeStaticData() throws Exception {
		objUtility.killProcess();
		Log.info(objDir.deleteDirectory(new File(Constants.TEMP_FILES)));
		extent = ExtentManager.GetExtent();
		objExcel.createTestResultExcel();
		
	}

	@Test
	void Start() throws IOException {
		try {

			Object[][] ExcelData = null;
			int rowCount = 0;

			String ScName, ObjectType, ObjectXpathAlias, ObjectValue, Excute, StrValue;
			String TcBeginStatus = "";

			String suitName[] = Constants.SUIT_TO_RUN.split(Constants.DELIMA_COMMA);

			for (int i = 0; i < suitName.length; i++) {
				ExcelData = objExcel.getSuit(suitName[i]);
				Constants.CURRENT_SUIT = suitName[i];
				objExcel.getObjectRepository(suitName[i]);
				objExcel.LogReport(suitName[i],  "Step Name","Message", "Status");
				objExcel.LogReport(suitName[i],"Creating Object Repository For "+suitName[i]," Operation Successful.", "PASS");		

				rowCount = ExcelData.length;

				for (int j = 1; j < rowCount; j++) {
					//#######################################################################
					//#######################################################################
					ScName = (String) ExcelData[j][0]; 														 //#####################
					Constants.TEMP_MSG = ScName;
					ObjectType = (String) ExcelData[j][1];// Name Of Action               #####################
					ObjectXpathAlias = (String) ExcelData[j][2];// Locator                  #####################
					ObjectValue = (String) ExcelData[j][3];                                               // #####################
					StrValue = (String) ExcelData[j][4]; 								                         //#####################
					Excute = (String) ExcelData[j][5];															 // #####################
					// #######################################################################
					// #######################################################################

					if (ObjectXpathAlias.equals("TCBEGIN") && Excute.equals("Y")) {
						Log.info("TC START: " + ScName + " ,Alias: " + ObjectXpathAlias);

						TcBeginStatus = "Y";
						objExcel.LogReport(suitName[i],  ScName,"TestCase for "+ScName+" Started", ObjectXpathAlias);	
						Report.extentCreateTest(ScName, "Test case for "+ScName+" Started");
					}
					if (ObjectXpathAlias.equals("TCEND") && Excute.equals("Y")) {
						Log.info("TC TCEND: " + ScName + " ,Alias: " + ObjectXpathAlias);
						Constants.IGN_NEXT_EXE_COND = "N";
						TcBeginStatus = "Y";
						objExcel.LogReport(suitName[i],  ScName,"TestCase for "+ScName+" Finished",ObjectXpathAlias);	
						//test = extent.createTest("TC END: " + ScName);
					}
					
					// #####################################################################
					if (ObjectType.equals(" ") || Excute.equals(null) || Excute.equals("N")) 
					{
						if (TcBeginStatus.equals("Y")) 
						{
							Log.info("Skip This Row. Excute: N");
						}
					} 
					else
					{
						if (Constants.IGN_NEXT_EXE_COND.equals("Y")) 
						{
							Log.warn("Skipping ScName=" + ScName);
							//if (ObjectValue.equals("LOGREPORT")) 
							//{
								//objExcel.LogReport(suitName[i],ScName,"Skipping This Step", "SKIP");	
								Report.logReort("Skipping This Step" ,Constants.SKIP);
							//}
							if (ObjectXpathAlias.equals("TCEND")) 
							{
								Log.info("Setting IGNEXECCOND back to N");
								Constants.IGN_NEXT_EXE_COND = "N";
								objExcel.LogReport(suitName[i],  ScName,"TestCase for "+ScName+" Finished", ObjectXpathAlias);
							}
						 } 
						else
						{
							if (!ObjectType.equals("") && !ObjectXpathAlias.equals("TCEND") && !ObjectXpathAlias.equals("TCBEGIN")) {

								String objLocator = objExcel.getObjectSelector(ObjectXpathAlias);

								if (!objLocator.equals(null)) 
								{
									if (!ScName.equals(null) && !ScName.equals("")) 
									{
										Log.info("Step# " + ScName + ", Alias#  " + ObjectXpathAlias + " Selector#  "+ objLocator + " ObjectType#  " + ObjectType);
										objKeyWordBuilder.Operation(ObjectType, objLocator, ObjectValue, StrValue);
									}
								}
							}
						}
					}
					// #####################################################################
				}
			}
		} catch (Exception e) {
			Log.error("Exception: " + e.getMessage());
		}
	}

	@AfterClass
	public void tear() {
		extent.flush();
		// objUtility.killProcess();
	}

}