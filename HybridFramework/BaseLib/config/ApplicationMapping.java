package config;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class ApplicationMapping {

	private static ApplicationMapping instance;
	private static HashMap<String, String> AppMap = new HashMap<String, String>();
	private static HashMap<String, String> ObjRepoMap = new HashMap<String, String>();

	private static Logger Log = Logger.getLogger(ApplicationMapping.class.getName());

	private ApplicationMapping() {
		Log.info("Singleton(): Initializing Instance");
	}

	public static ApplicationMapping getInstance() {
		if (instance == null) {
			synchronized (ApplicationMapping.class) {
				if (instance == null) {
					instance = new ApplicationMapping();
				}
			}
		}
		ApplicationMapping.AppMap();
		ApplicationMapping.ObjRepoMap();
		return instance;
	}

	private static void AppMap() {
		try {
			AppMap.put("OrangeHRMLoginObjectRepository", "OrangeHRMLogin");
			AppMap.put("OrangeHRMObjRepo", "OrangeHRM");
			AppMap.put("ADMINOBJREPO", "ADMIN");
		} catch (Exception objException) {
			Log.error("Application Mapping Error.");
		}
	}

	private static void ObjRepoMap() {
		try {
			ObjRepoMap.put("OrangeHRMLoginDataFlow", "OrangeHRMLoginObjectRepository");
			ObjRepoMap.put("OrangeHRMLoginQA", "OrangeHRMObjRepo");
			ObjRepoMap.put("ADMINREG", "ADMINOBJREPO");
			ObjRepoMap.put("DUDE", "ADMINOBJREPO");
		} catch (Exception objException) {
			Log.error("Object Repository Mapping Error.");
		}
	}

	public String getAppName(String SuitToRun) {
		return AppMap.get(ObjRepoMap.get(SuitToRun));
	}

}
