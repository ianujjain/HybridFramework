package helper;

import java.util.Random;

import org.apache.log4j.Logger;

import config.ApplicationMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import helper.Constants;

public class Utility {

	Logger Log = Logger.getLogger(Utility.class.getName());
	private static Utility instance;

	private Utility() {
		Log.info("Singleton(): Initializing Instance");
	}

	public static Utility getInstance() {
		if (instance == null) {
			synchronized (ApplicationMapping.class) {
				if (instance == null) {
					instance = new Utility();
				}
			}
		}
		return instance;
	}

	public String getRandomString(final int min, final int max) {
		try {
			StringBuffer randStr = new StringBuffer();
			int length = getRandomNumber(min,max);
			if (length <= 0) {
				Log.error("Length Must Be Greater Than Zero...");
			}

			for (int i = 0; i < length; i++) {
				int number = randomNumber();
				char ch = Constants.CHARLIST.charAt(number);
				randStr.append(ch);
			}
			return randStr.toString();
		} catch (Exception objException) {
			Log.error(objException.getMessage());
			return "";
		}
	}

	private int randomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(Constants.CHARLIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public int getRandomNumber(final int min, final int max) {
		try {
			if (min >= max) {
				Log.error("Max Must Be Greater Than Min...");
			}
			Random r = new Random();
			return r.nextInt((max - min) + 1) + min;
		} catch (Exception objException) {
			Log.error(objException.getMessage());
			return 0;
		}
	}

	public String Timestamp() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

		return "_" + (((ft.format(dNow).toString()).replace(" ", "_")).replace(":", "_")).replace(".", "_");
	}

	public String TimestampYYYYMMDD() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");

		return (((ft.format(dNow).toString()).replace(" ", "-")).replace(":", "-")).replace(".", "-");
	}

	public void killProcess() {
		String command = "cmd /c start " + Constants.KILL_PROCESS;
		try {
			Runtime.getRuntime().exec(command);
			Log.info("Command Execute: " + command);
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
	}
}