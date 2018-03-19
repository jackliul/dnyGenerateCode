package util;

import java.util.ResourceBundle;


public class NewPropertiesReaderUtil {
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

		GENERATE_TABLE_ID = getGenerate_table_id();

		if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0))
			DATABASE_TYPE = DATABASE_TYPE_MYSQL;
		else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
			DATABASE_TYPE = DATABASE_TYPE_ORACLE;
		}

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

	public static final String getTEMPLATEPATH() {
		return bundlePath.getString("templatePath");
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
