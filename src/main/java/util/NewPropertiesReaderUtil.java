package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class NewPropertiesReaderUtil {

	public static ResourceBundle dbBundle = null;
	public static ResourceBundle generateBundlePath = null;

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
		dbBundle = getBundle(dbBundle, "db.properties");
		generateBundlePath = getBundle(generateBundlePath, "generate.properties");

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

	private static ResourceBundle getBundle(ResourceBundle rb, String propertiesFileName) {
		String proFilePath = System.getProperty("user.dir") + File.separator + "configs" + File.separator
				+ propertiesFileName;
		System.out.println("proFilePath " + proFilePath);
		BufferedInputStream inputStream;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(proFilePath));
			rb = new PropertyResourceBundle(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rb;
	}

	public static final String getDIVER_NAME() {
		String databaseName = generateBundlePath.getString("database");
		return dbBundle.getString(databaseName + "." + "driverClassName");
	}

	public static final String getURL() {
		String databaseName = generateBundlePath.getString("database");
		return dbBundle.getString(databaseName + "." + "url");
	}

	public static final String getUSERNAME() {
		String databaseName = generateBundlePath.getString("database");
		return dbBundle.getString(databaseName + "." + "username");
	}

	public static final String getPASSWORD() {
		String databaseName = generateBundlePath.getString("database");
		return dbBundle.getString(databaseName + "." + "password");
	}

	public static final String getDATABASE_NAME() {
		String databaseName = generateBundlePath.getString("database");
		return dbBundle.getString(databaseName + "." + "database_name");
	}

	public static final String getTEMPLATEPATH() {
		return generateBundlePath.getString("templatePath");
	}

	public static final String getSYSTEM_ENCODING() {
		return generateBundlePath.getString("system_encoding");
	}

	public static final String getGenerate_table_id() {
		return generateBundlePath.getString("generate_table_id");
	}

	public static final String getGenerate_ui_filter_fields() {
		return generateBundlePath.getString("generate_ui_filter_fields");
	}

	public static final String getUi_search_filed_num() {
		return generateBundlePath.getString("ui_search_filed_num");
	}

	public static final String getUi_field_required_num() {
		return generateBundlePath.getString("ui_field_required_num");
	}

	public static final String getConfigInfo(String key) {
		return generateBundlePath.getString(key);
	}

}
