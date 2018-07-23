package generateCore;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import table.ColumnData;
import table.CreateBean;
import util.NewPropertiesReaderUtil;

public class CodeGenerateFactoryFpxXmsCts {

	// private static final Log log =
	// LogFactory.getLog(CodeGenerateFactoryFpxXmsCts.class);

	// jdbc 连接相关
	private static String url;
	private static String username;
	private static String passWord;
	private static CreateBean createBean;
	private static Configuration cfg;

	// 动态生成模块配置
	private static Boolean hasController;
	private static Boolean hasService;
	private static Boolean hasMapperPath;
	private static Boolean hasSqlMap;
	private static Boolean hasPo;
	private static Boolean hasQueryVO;
	private static Boolean hasInfoVO;
	private static Boolean hasJspPage;

	// freemark配置
	private static Map<String, Object> root;
	private static String srcPath;

	// 生成的文件类型
	private static String javaFile = ".java";
	private static String xmlFile = ".xml";
	private static String jspFile = ".jsp";

	public static void generateCode(String tableName, String codeName, String keyType) {
		init();

		String className = createBean.getTablesNameToClassName(tableName);
		String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
		root.put("className", className);
		root.put("lowerName", lowerName);

		root.put("keyType", keyType);
		root.put("tableName", tableName);
		root.put("codeName", codeName);

		String poPackage = NewPropertiesReaderUtil.getConfigInfo("poPackage");
		String servicePackage = NewPropertiesReaderUtil.getConfigInfo("servicePath");
		root.put("poPackage", poPackage);
		root.put("servicePackage", servicePackage);

		generateController();
		generateService();
		generateMapperClass();
		generateSqlMapper(tableName, className);
		generatePO(tableName, className);
		generateQueryVO();
		generateInfoVO(tableName, className);
		generateJspPage();
	}

	private static void generateController() {
		if (hasController) {
			String controllerPackage = NewPropertiesReaderUtil.getConfigInfo("controllerPackage");
			root.put("controllerPackage", controllerPackage);

			String controllerName = root.get("className") + "Controller";
			String controllerPath = NewPropertiesReaderUtil.getConfigInfo("controllerPath") + controllerName + javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "controllerClass.ftl", srcPath, controllerPath);
		}
	}

	private static void generateService() {
		if (hasService) {
			root.put("servicePackage", NewPropertiesReaderUtil.getConfigInfo("servicePackage"));
			String serviceName = "I" + root.get("className") + "Service";
			String servicePath = NewPropertiesReaderUtil.getConfigInfo("servicePath") + serviceName + javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "serviceClass.ftl", srcPath, servicePath);

			root.put("mapperPackage", NewPropertiesReaderUtil.getConfigInfo("mapperPackage"));

			root.put("serviceImplPackage", NewPropertiesReaderUtil.getConfigInfo("serviceImplPackage"));
			String serviceImplName = root.get("className") + "ServiceImpl";
			String serviceImplPath = NewPropertiesReaderUtil.getConfigInfo("serviceImplPath") + serviceImplName
					+ javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "serviceImplClass.ftl", srcPath, serviceImplPath);
		}
	}

	private static void generateMapperClass() {
		if (hasMapperPath) {
			root.put("mapperPackage", NewPropertiesReaderUtil.getConfigInfo("mapperPackage"));

			String mapperName = root.get("className") + "Mapper";
			String mapperPath = NewPropertiesReaderUtil.getConfigInfo("mapperPath") + mapperName + javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "mapperClass.ftl", srcPath, mapperPath);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void generateSqlMapper(String tableName, String className) {

		if (hasSqlMap) {
			List columnDatas = null;
			String feilds = null;
			try {
				columnDatas = createBean.getColumnDatas(tableName);
				feilds = createBean.getBeanFeilds(tableName, className, false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			List columnKeyDatas = createBean.getColumnKeyDatas(columnDatas);
			String tablesAsName = createBean.getTablesASName(tableName);

			root.put("mapperPackage", NewPropertiesReaderUtil.getConfigInfo("mapperPackage"));
			root.put("feilds", feilds);
			root.put("tablesAsName", tablesAsName);
			root.put("columnDatas", columnDatas);
			ColumnData keyColu = (ColumnData) columnKeyDatas.get(0);
			root.put("keyColu", keyColu.getColumnName()); // 可能会有多个主键，此处默认只有一个主键取出来用于数据库子增长

			// root.put("queryfeilds", createBean.getQueryBeanFeilds(tableName, className));
			// root.put("columnKeyDatas", columnKeyDatas);
			// root.put("columnKeyParam", columnKeyParam);
			// root.put("columnKeyUseParam", columnKeyUseParam);
			// root.put("SQL", sqlMap);

			String mapperName = root.get("className") + "Mapper";
			String mapperPath = NewPropertiesReaderUtil.getConfigInfo("mapperPath") + mapperName + xmlFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap.ftl", srcPath, mapperPath);
		}
	}

	private static void generatePO(String tableName, String className) {
		if (hasPo) {
			String feilds = null;
			try {
				feilds = createBean.getBeanFeilds(tableName, className, false);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			root.put("poPackage", NewPropertiesReaderUtil.getConfigInfo("poPackage"));
			root.put("feilds", feilds);

			String poName = root.get("className") + "PO";
			String poPath = NewPropertiesReaderUtil.getConfigInfo("poPath") + poName + javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "po.ftl", srcPath, poPath);
		}
	}

	private static void generateQueryVO() {
		if (hasQueryVO) {
			root.put("queryVoPackage", NewPropertiesReaderUtil.getConfigInfo("queryVoPackage"));

			String queryVoName = root.get("className") + "QueryVO";
			String queryVoPath = NewPropertiesReaderUtil.getConfigInfo("queryVoPath") + queryVoName + javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "queryVo.ftl", srcPath, queryVoPath);
		}
	}

	private static void generateInfoVO(String tableName, String className) {
		if (hasInfoVO) {
			String feilds = null;
			try {
				feilds = createBean.getBeanFeilds(tableName, className, true);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			root.put("infoVoPackage", NewPropertiesReaderUtil.getConfigInfo("infoVoPackage"));
			root.put("feilds", feilds);

			String infoVoName = root.get("className") + "DTO";
			String infoVoPath = NewPropertiesReaderUtil.getConfigInfo("infoVoPath") + infoVoName + javaFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "dto.ftl", srcPath, infoVoPath);
		}
	}

	private static void generateJspPage() {
		if (hasJspPage) {
			String jspPageName = root.get("className") + "";
			String jspPagePath = NewPropertiesReaderUtil.getConfigInfo("jspPagePath") + jspPageName + jspFile;
			FreemarkerEngine.createFileByFTL(cfg, root, "jspManager.ftl", srcPath, jspPagePath);
		}
	}

	private static String getClassPath() {
		String path = new CodeGenerateFactoryFpxXmsCts().getClass().getResource("/").getPath();
		return path;
	}

	private CodeGenerateFactoryFpxXmsCts() {
	}

	private static void init() {
		// freemark配置
		cfg = new Configuration();
		String templateBasePath = getClassPath() + NewPropertiesReaderUtil.getConfigInfo("templatePath");
		try {
			cfg.setDirectoryForTemplateLoading(new File(templateBasePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		root = new HashMap<String, Object>();
		srcPath = NewPropertiesReaderUtil.getConfigInfo("srcPath");

		// jdbc 连接相关
		url = NewPropertiesReaderUtil.URL;
		username = NewPropertiesReaderUtil.USERNAME;
		passWord = NewPropertiesReaderUtil.PASSWORD;
		createBean = new CreateBean();
		createBean.setMysqlInfo(url, username, passWord);

		// 初始化数据
		root.put("author", NewPropertiesReaderUtil.getConfigInfo("author"));
		root.put("nowDate", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));

		// 动态生成模块配置
		hasController = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("controllerPath"))
				? true
				: false;
		hasService = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("servicePath")) ? true
				: false;
		hasMapperPath = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("mapperPath"))
				? true
				: false;
		hasSqlMap = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("sqlMapPath")) ? true
				: false;
		hasPo = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("poPath")) ? true : false;
		hasQueryVO = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("queryVoPath")) ? true
				: false;
		hasInfoVO = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("infoVoPath")) ? true
				: false;
		hasJspPage = StringUtils.isNotEmpty(NewPropertiesReaderUtil.generateBundlePath.getString("jspPagePath")) ? true
				: false;
	}

}
