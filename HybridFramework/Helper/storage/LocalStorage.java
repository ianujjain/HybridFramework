package storage;

public class LocalStorage {

	public static String USR_NAME = "";

	public static String getValue(final String keyWard) {

		String tempData = "";

		switch (keyWard) {
		case "USERNAME":
			tempData = USR_NAME;
			break;
		}
		return tempData;
	}
}
