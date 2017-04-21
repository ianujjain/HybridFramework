package filesystem;

import org.apache.log4j.Logger;

import helper.Constants;
import helper.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;

import config.ApplicationMapping;

public class Excel {

	private static Excel instance;

	private Directory objDirectory = null;
	private Utility objUtility = null;
	private Sheet ExcelSheet = null;
	private XSSFWorkbook ExcelWorkbook = null;
	private XSSFCell Cell;

	private FileInputStream ExcelFile = null;
	public Map<String, String> objRepo = null;

	Logger Log = Logger.getLogger(Excel.class.getName());

	private Excel() {
		Log.info("Excel(): Initializing Instance");
	}

	public static Excel getInstance() {
		if (instance == null) {
			synchronized (ApplicationMapping.class) {
				if (instance == null) {
					instance = new Excel();
				}
			}
		}
		return instance;
	}

	public void SetExcelPath(String excelFilePath) throws Exception {
		try {
			ExcelFile = new FileInputStream(new File(excelFilePath));
			ExcelWorkbook = new XSSFWorkbook(ExcelFile);
			Log.info("Excel File Path Set: " + excelFilePath);
		} catch (Exception e) {
			Log.error("Class Excel | Method SetExcelPath | Exception Desc : " + e.getMessage());
		}
	}

	public void getObjectRepository(String suitToRun) throws Exception {

		String fileLoc[] = Constants.OBJ_REPO_PATH.split(Constants.DELIMA_COMMA);
		String excelFilePath = fileLoc[0] + ApplicationMapping.getInstance().getAppName(suitToRun) + "." + fileLoc[1];

		ExcelFile = new FileInputStream(new File(excelFilePath));

		ExcelWorkbook = new XSSFWorkbook(ExcelFile);
		Log.info("Excel File Path Set: " + excelFilePath);

		objRepo = new HashMap<String, String>();
		ExcelSheet = ExcelWorkbook.getSheetAt(0);
		Log.info("Read Excel Sheet: " + ExcelWorkbook.getSheetName(0));
		try {
			int RowCount = 0;

			RowCount = ExcelSheet.getPhysicalNumberOfRows();

			for (int Row = 2; Row < RowCount; Row++) {

				String AliasName = GetCellData(Row, 0, ExcelWorkbook.getSheetName(0));
				String ObjectMethodSelecteor = GetCellData(Row, 4, ExcelWorkbook.getSheetName(0));

				// String ObjectMethodSelecteor = GetCellData(Row,
				// 1,ExcelWorkbook.getSheetName(0))
				// + ":"
				// + GetCellData(Row, 2, ExcelWorkbook.getSheetName(0));

				if (!objRepo.containsKey(AliasName)) {
					objRepo.put(AliasName, ObjectMethodSelecteor);
				} else {
					Log.warn("Key Already Exists: " + AliasName);
				}
			}
			Log.info("Object Repository Successfully Created. No of Records: " + objRepo.size());
			// return (HashMap<String, String>) objRepo;
		} catch (Exception ObjException) {
			Log.error(ObjException.getMessage());
			// return null;
		} finally {
			ExcelWorkbook.close();
		}

	}

	public HashMap<String, String> GetObjectRepository() {
		try {
			return (HashMap<String, String>) objRepo;
		} catch (Exception ObjException) {
			Log.error("Class Excel | Method GetObjectRepository | Exception Desc: " + ObjException.getMessage());
			return null;
		}
	}

	public int getNumberOfSheets() {
		return ExcelWorkbook.getNumberOfSheets();
	}

	public int GetRowCount(String SheetName) {
		int iNumber = 0;
		try {
			ExcelSheet = ExcelWorkbook.getSheet(SheetName);
			iNumber = ExcelSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			Log.error("Class Utils | Method GetRowCount | Exception desc : " + e.getMessage());
			// DriverScript.bResult = false;
		}
		return iNumber;
	}

	public int GetColumnsCount(String SheetName, int rowIndex) {
		int iNumber = 0;
		try {
			ExcelSheet = ExcelWorkbook.getSheet(SheetName);
			iNumber = ExcelWorkbook.getSheet(SheetName).getRow(rowIndex).getPhysicalNumberOfCells() + 1;
		} catch (Exception e) {
			Log.error("Class Utils | Method GetRowCount | Exception desc : " + e.getMessage());
			// DriverScript.bResult = false;
		}
		return iNumber;
	}

	public String GetCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try {
			ExcelSheet = ExcelWorkbook.getSheet(SheetName);
			Cell = (XSSFCell) ExcelSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue().toString();
			return CellData;
		} catch (Exception e) {
			Log.error("Class Utils | Method getCellData | Exception desc : " + e.getMessage());
			// DriverScript.bResult = false;
			return "";
		}
	}

	public void createTestResultExcel() {
		try {

			objDirectory = Directory.getInstance();
			objUtility = Utility.getInstance();
			String dirLoc = Constants.TEST_RESULT_DIR + objUtility.TimestampYYYYMMDD() + "\\";
			String fileLoc[] = Constants.TEST_RESULT_FILE_NAME.split(Constants.DELIMA_COMMA);
			String filePath = dirLoc + fileLoc[0] + objUtility.Timestamp() + "." + fileLoc[1];

			if (Constants.TEST_RESULT_PATH == null && Constants.SUIT_TO_RUN != null) {

				Constants.TEST_RESULT_PATH = filePath;
				Log.info("File. Path: " + filePath);

				objDirectory.createDirectory(dirLoc);
				FileOutputStream fos = new FileOutputStream(filePath);
				ExcelWorkbook = new XSSFWorkbook();

				String suitName[] = Constants.SUIT_TO_RUN.split(Constants.DELIMA_COMMA);
				for (int i = 0; i < suitName.length; i++) {
					ExcelSheet = ExcelWorkbook.createSheet(suitName[i]);
				}

				ExcelWorkbook.write(fos);
				fos.flush();
				fos.close();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void LogReport(String sheetName, String ScName, String StrValue, String testStatus) throws IOException {

		FileInputStream fsIP = new FileInputStream(new File(Constants.TEST_RESULT_PATH));

		// FileInputStream fsIP = new FileInputStream(new
		// File("D:/Study/Java/HybridFramework/TestResult/12-03-2017/A.xlsx"));

		ExcelWorkbook = new XSSFWorkbook(fsIP);
		ExcelSheet = ExcelWorkbook.getSheet(sheetName);

		ExcelWorkbook.getSheet(sheetName).autoSizeColumn(0);
		ExcelWorkbook.getSheet(sheetName).autoSizeColumn(1);
		ExcelWorkbook.getSheet(sheetName).autoSizeColumn(2);

		int rownum = ExcelSheet.getPhysicalNumberOfRows();
		System.out.print(rownum);

		if (testStatus.equalsIgnoreCase("Status")) {

			Row rowHeader = ExcelSheet.createRow(rownum);

			CellStyle styleHeader = ExcelWorkbook.createCellStyle();

			styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
			styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
			styleHeader.setBorderRight(CellStyle.BORDER_THIN);
			styleHeader.setBorderTop(CellStyle.BORDER_THIN);

			styleHeader.setFillForegroundColor(IndexedColors.GOLD.getIndex());
			styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);

			Cell cellHeader1 = rowHeader.createCell(0);
			Cell cellHeader2 = rowHeader.createCell(1);
			Cell cellHeader3 = rowHeader.createCell(2);

			if (ScName instanceof String) {
				cellHeader1.setCellValue((String) ScName);
				cellHeader1.setCellStyle(styleHeader);
			}

			if (StrValue instanceof String) {
				cellHeader2.setCellValue((String) StrValue);
				cellHeader2.setCellStyle(styleHeader);
			}
			if (testStatus instanceof String) {
				cellHeader3.setCellValue((String) testStatus);
				cellHeader3.setCellStyle(styleHeader);
			}
		} else if (testStatus.equalsIgnoreCase("TCBEGIN")) {
			Row rowHeader = ExcelSheet.createRow(rownum);

			CellStyle styleTCBEGIN = ExcelWorkbook.createCellStyle();

			styleTCBEGIN.setBorderBottom(CellStyle.BORDER_THIN);
			styleTCBEGIN.setBorderLeft(CellStyle.BORDER_THIN);
			styleTCBEGIN.setBorderRight(CellStyle.BORDER_THIN);
			styleTCBEGIN.setBorderTop(CellStyle.BORDER_THIN);

			styleTCBEGIN.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			styleTCBEGIN.setFillPattern(CellStyle.SOLID_FOREGROUND);

			Cell cellTCBEGIN1 = rowHeader.createCell(0);
			Cell cellTCBEGIN2 = rowHeader.createCell(1);
			Cell cellTCBEGIN3 = rowHeader.createCell(2);

			if (ScName instanceof String) {
				cellTCBEGIN1.setCellValue((String) ScName);
				cellTCBEGIN1.setCellStyle(styleTCBEGIN);
			}

			if (StrValue instanceof String) {
				cellTCBEGIN2.setCellValue((String) StrValue);
				cellTCBEGIN2.setCellStyle(styleTCBEGIN);
			}
			if (testStatus instanceof String) {
				cellTCBEGIN3.setCellValue((String) testStatus);
				cellTCBEGIN3.setCellStyle(styleTCBEGIN);
			}
		} else if (testStatus.equalsIgnoreCase("TCEND")) {
			Row rowHeader = ExcelSheet.createRow(rownum);

			CellStyle styleTCEND = ExcelWorkbook.createCellStyle();

			styleTCEND.setBorderBottom(CellStyle.BORDER_THIN);
			styleTCEND.setBorderLeft(CellStyle.BORDER_THIN);
			styleTCEND.setBorderRight(CellStyle.BORDER_THIN);
			styleTCEND.setBorderTop(CellStyle.BORDER_THIN);

			styleTCEND.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			styleTCEND.setFillPattern(CellStyle.SOLID_FOREGROUND);

			Cell cellTCEND1 = rowHeader.createCell(0);
			Cell cellTCEND2 = rowHeader.createCell(1);
			Cell cellTCEND3 = rowHeader.createCell(2);

			if (ScName instanceof String) {
				cellTCEND1.setCellValue((String) ScName);
				cellTCEND1.setCellStyle(styleTCEND);
			}

			if (StrValue instanceof String) {
				cellTCEND2.setCellValue((String) StrValue);
				cellTCEND2.setCellStyle(styleTCEND);
			}
			if (testStatus instanceof String) {
				cellTCEND3.setCellValue((String) testStatus);
				cellTCEND3.setCellStyle(styleTCEND);
			}
		} else {
			Row row = ExcelSheet.createRow(rownum++);

			Font font1 = ExcelWorkbook.createFont();
			CellStyle style1 = ExcelWorkbook.createCellStyle();
			style1.setBorderBottom(CellStyle.BORDER_THIN);
			style1.setBorderLeft(CellStyle.BORDER_THIN);
			style1.setBorderRight(CellStyle.BORDER_THIN);
			style1.setBorderTop(CellStyle.BORDER_THIN);

			Font font2 = ExcelWorkbook.createFont();
			CellStyle style2 = ExcelWorkbook.createCellStyle();
			style2.setBorderBottom(CellStyle.BORDER_THIN);
			style2.setBorderLeft(CellStyle.BORDER_THIN);
			style2.setBorderRight(CellStyle.BORDER_THIN);
			style2.setBorderTop(CellStyle.BORDER_THIN);
			
			Font font3 = ExcelWorkbook.createFont();
			CellStyle style3 = ExcelWorkbook.createCellStyle();
			style3.setBorderBottom(CellStyle.BORDER_THIN);
			style3.setBorderLeft(CellStyle.BORDER_THIN);
			style3.setBorderRight(CellStyle.BORDER_THIN);
			style3.setBorderTop(CellStyle.BORDER_THIN);

			if (testStatus.equalsIgnoreCase(Constants.PASS)) {

				Cell cell1 = row.createCell(0);
				Cell cell2 = row.createCell(1);
				Cell cell3 = row.createCell(2);

				if (ScName instanceof String && testStatus.equalsIgnoreCase(Constants.PASS)) {
					font1.setColor(HSSFColor.BLACK.index);
					style1.setFont(font1);
					cell1.setCellValue((String) ScName);
					cell1.setCellStyle(style1);
				}

				if (StrValue instanceof String && testStatus.equalsIgnoreCase(Constants.PASS)) {
					font2.setColor(HSSFColor.BLACK.index);
					style2.setFont(font2);
					cell2.setCellValue((String) StrValue);
					cell2.setCellStyle(style2);
				}
				if (testStatus instanceof String && testStatus.equalsIgnoreCase(Constants.PASS)) {
					font3.setColor(HSSFColor.GREEN.index);
					font3.setBold(true);
					style3.setFont(font3);
					cell3.setCellValue((String) testStatus);
					cell3.setCellStyle(style3);
				}
			}

			if (testStatus.equalsIgnoreCase(Constants.FAIL) || testStatus.equalsIgnoreCase(Constants.SKIP)) {
				Cell cellFail1 = row.createCell(0);
				Cell cellFail2 = row.createCell(1);
				Cell cellFail3 = row.createCell(2);

				if (ScName instanceof String) {
					font1.setColor(HSSFColor.BLACK.index);
					style1.setFont(font1);
					cellFail1.setCellValue((String) ScName);
					cellFail1.setCellStyle(style1);
				}
				if (StrValue instanceof String) {
					font2.setColor(HSSFColor.BLACK.index);
					style2.setFont(font2);
					cellFail2.setCellValue((String) StrValue);
					cellFail2.setCellStyle(style2);
				}
				if (testStatus instanceof String) {
					font2.setColor(HSSFColor.RED.index);
					font2.setBold(true);
					style2.setFont(font2);
					cellFail3.setCellValue((String) testStatus);
					cellFail3.setCellStyle(style2);
				}
			}

		}

		try {
			FileOutputStream out = new FileOutputStream(new File(Constants.TEST_RESULT_PATH));

			// FileOutputStream out = new FileOutputStream(new
			// File("D:/Study/Java/HybridFramework/TestResult/12-03-2017/A.xlsx"));

			ExcelWorkbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object[][] getSuit(final String currentSuitName) throws IOException {

		Object[][] ExcelData = null;
		int RowCount = 0, CellsCount = 0;
		int rowCount = 0, colCount = 0;

		String fileLoc[] = Constants.OBJ_REPO_PATH.split(Constants.DELIMA_COMMA);
		String excelFilePath = fileLoc[0] + ApplicationMapping.getInstance().getAppName(currentSuitName) + "."
				+ fileLoc[1];

		File file = new File(excelFilePath);

		if (file.exists() && !file.isDirectory()) {
			FileInputStream inputStream = new FileInputStream(file);

			XSSFWorkbook excelWorkbook = new XSSFWorkbook(inputStream);
			XSSFSheet excelSheet = excelWorkbook.getSheet(currentSuitName);

			RowCount = excelSheet.getPhysicalNumberOfRows();
			CellsCount = excelSheet.getRow(0).getPhysicalNumberOfCells();

			ExcelData = new String[RowCount][CellsCount];

			Iterator<Row> rowIterator = excelSheet.iterator();

			while (rowIterator.hasNext()) {
				if (rowCount < RowCount) {
					Row nextRow = rowIterator.next();

					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						if (colCount < CellsCount) {
							Cell cell = cellIterator.next();

							switch (cell.getCellType()) {
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
								ExcelData[rowCount][colCount] = cell.getStringCellValue();
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
								ExcelData[rowCount][colCount] = cell.getBooleanCellValue();
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR:
								ExcelData[rowCount][colCount] = cell.getStringCellValue();
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
								ExcelData[rowCount][colCount] = cell.getStringCellValue();
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
								ExcelData[rowCount][colCount] = cell.getNumericCellValue();
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
								ExcelData[rowCount][colCount] = cell.getStringCellValue();
								break;
							}
							// System.out.println(rowCount + " | " + colCount);
							colCount++;
						}
					}
					colCount = 0;
				} else {
					break;
				}
				rowCount++;
			}

			excelWorkbook.close();
			inputStream.close();

		} else {
			ExcelData = null;
			Log.warn("Test Case Sheet : " + Constants.SUIT_TO_RUN + " does not exist");
		}
		return ExcelData;
	}

	public String getObjectSelector(final String objectAliasName) {
		// AppMap.get(ObjRepoMap.get(SuitToRun))
		return objRepo.get(objectAliasName);
	}

}