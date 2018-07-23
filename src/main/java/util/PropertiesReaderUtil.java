package util;

import java.util.ResourceBundle;


public class PropertiesReaderUtil {
    public static final ResourceBundle bundle = ResourceBundle.getBundle("configs/db");
	public static final ResourceBundle bundlePath = ResourceBundle.getBundle("configs/generate");

	public static String DIVER_NAME;

	public static String URL;

	public static String USERNAME;

	public static String PASSWORD;

	public static String DATABASE_NAME;

	public static String DATABASE_TYPE = "mysql";
	public static String DATABASE_TYPE_MYSQL = "mysql";
	public static String DATABASE_TYPE_ORACLE = "oracle";

	public static String JEECG_UI_FIELD_REQUIRED_NUM = "4";

	public static String UI_FIELD_SEARCH_NUM = "3";

	public static String source_root_package = "src";

	public static String bussiPackage = "sun";
	public static String bussiPackageUrl = "sun";

	public static String entity_package = "entity";

	public static String page_package = "page";

	public static String ENTITY_URL = source_root_package + "/" + bussiPackageUrl + "/" + entity_package + "/";

	public static String PAGE_URL = source_root_package + "/" + bussiPackageUrl + "/" + page_package + "/";

	public static String ENTITY_URL_INX = bussiPackage + "." + entity_package + ".";

	public static String PAGE_URL_INX = bussiPackage + "." + page_package + ".";
	public static String TEMPLATEPATH;
	public static String CODEPATH = source_root_package + "/" + bussiPackageUrl + "/";

	public static String GENERATE_TABLE_ID;
	public static String JEECG_GENERATE_UI_FILTER_FIELDS;
	public static String SYSTEM_ENCODING;

	static {
		DIVER_NAME = getDIVER_NAME();
		URL = getURL();
		USERNAME = getUSERNAME();
		PASSWORD = getPASSWORD();
		DATABASE_NAME = getDATABASE_NAME();

		SYSTEM_ENCODING = getSYSTEM_ENCODING();
		TEMPLATEPATH = getTEMPLATEPATH();
		source_root_package = getSourceRootPackage();
		bussiPackage = getBussiPackage();
		bussiPackageUrl = bussiPackage.replace(".", "/");

		GENERATE_TABLE_ID = getGenerate_table_id();

		UI_FIELD_SEARCH_NUM = getUi_search_filed_num();
		if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0))
			DATABASE_TYPE = DATABASE_TYPE_MYSQL;
		else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
			DATABASE_TYPE = DATABASE_TYPE_ORACLE;
		}

		source_root_package = source_root_package.replace(".", "/");
	}

	public static final String getDIVER_NAME() {
		String databaseName = bundlePath.getString("database");
		return bundle.getString(databaseName + "." + "driverClassName");
	}

	public static final String getURL() {
		String databaseName = bundlePath.getString("database");
		return bundle.getString(databaseName + "." + "url");
	}

	public static final String getUSERNAME() {
		String databaseName = bundlePath.getString("database");
		return bundle.getString(databaseName + "." + "username");
	}

	public static final String getPASSWORD() {
		String databaseName = bundlePath.getString("database");
		return bundle.getString(databaseName + "." + "password");
	}

	public static final String getDATABASE_NAME() {
		String databaseName = bundlePath.getString("database");
		return bundle.getString(databaseName + "." + "database_name");
	}

	private static String getBussiPackage() {
		return bundlePath.getString("service_package");
	}

	public static final String getEntityPackage() {
		return bundlePath.getString("entity_package");
	}

	public static final String getPagePackage() {
		return bundlePath.getString("page_package");
	}

	public static final String getTEMPLATEPATH() {
		return bundlePath.getString("templatePath");
	}

	public static final String getSourceRootPackage() {
		return bundlePath.getString("source_root_package");
	}

	public static final String getSYSTEM_ENCODING() {
		return bundlePath.getString("system_encoding");
	}

	public static final String getGenerate_table_id() {
		return bundlePath.getString("generate_table_id");
	}

	public static final String getGenerate_ui_filter_fields() {
		return bundlePath.getString("generate_ui_filter_fields");
	}

	public static final String getUi_search_filed_num() {
		return bundlePath.getString("ui_search_filed_num");
	}

	public static final String getUi_field_required_num() {
		return bundlePath.getString("ui_field_required_num");
	}

	public static final String getConfigInfo(String key) {
		return bundlePath.getString(key);
	}

}
