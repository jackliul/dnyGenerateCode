package generateCore;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import table.ColumnData;
import table.CreateBean;
import util.PropertiesReaderUtil;


public class CodeGenerateFactory {

	private static final Log log = LogFactory.getLog(CodeGenerateFactory.class);

	private static String url = PropertiesReaderUtil.URL;
	private static String username = PropertiesReaderUtil.USERNAME;
	private static String passWord = PropertiesReaderUtil.PASSWORD;

	public static String getClassPath() {
		String path = new CodeGenerateFactory().getClass().getResource("/").getPath();
		return path;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void codeGenerateByFTL(String tableName, String codeName, String keyType) throws Exception {

		log.debug("生成代码开始");
		System.out.println("生成代码开始");

		CreateBean createBean = new CreateBean();
		createBean.setMysqlInfo(url, username, passWord);
		String origClassName = createBean.getTablesNameToClassName(tableName);
		String className = origClassName + "New";
		String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
		String origLowerName = origClassName.substring(0, 1).toLowerCase()
				+ origClassName.substring(1, origClassName.length());
		String tableNameUpper = tableName.toUpperCase();
		String tablesAsName = createBean.getTablesASName(tableName);
		if (StringUtils.isEmpty(codeName)) {
			Map tableCommentMap = createBean.getTableCommentMap();
			codeName = (String) tableCommentMap.get(tableNameUpper);
		}
		String author = PropertiesReaderUtil.getConfigInfo("author");

		String pathSrc = PropertiesReaderUtil.getConfigInfo("path_src");
		String basePackage = PropertiesReaderUtil.getConfigInfo("base_package");
		String servicePackage = basePackage + "." + PropertiesReaderUtil.getConfigInfo("service_package") + "."
				+ className;
		String mapperPackage = basePackage + "." + PropertiesReaderUtil.getConfigInfo("mapper_package");
		String domainPackage = basePackage + "." + PropertiesReaderUtil.getConfigInfo("domain_package");
		String sqlmapPackage = basePackage + "." + PropertiesReaderUtil.getConfigInfo("sqlmap_package");
		String mapperPage = basePackage + "." + PropertiesReaderUtil.getConfigInfo("mapper_package");
		String serviceImplPackage = servicePackage + ".impl";
		String controllerPackage = basePackage + ".controller." + lowerName;
		String pagePackage = basePackage + ".page." + origLowerName + "-new";

		String servicePath = servicePackage.replace(".", "\\") + "\\" + className + "Service.java";
		String serviceImplPath = serviceImplPackage.replace(".", "\\") + "\\" + className + "ServiceImpl.java";
		String controllerPath = controllerPackage.replace(".", "\\") + "\\" + className + "Controller.java";
		String jspManagerPath = pagePackage.replace(".", "\\") + "\\" + lowerName + "Manager.jsp";
		String sqlMapperPath = sqlmapPackage.replace(".", "\\") + "\\" + className + "Mapper.xml";
		String domainPath = domainPackage.replace(".", "\\") + "\\" + className + ".java";
		String mapperPath = mapperPage.replace(".", "\\") + "\\" + className + "Mapper.java";

		// Map sqlMap = createBean.getAutoCreateSql(tableName);
		List columnDatas = createBean.getColumnDatas(tableName);
		List columnKeyDatas = createBean.getColumnKeyDatas(columnDatas);
		// String columnKeyParam = createBean.getColumnKeyParam(columnKeyDatas);
		// String columnKeyUseParam = createBean.getColumnKeyUseParam(columnKeyDatas);

		Map root = new HashMap();
		root.put("author", author);
		root.put("nowDate", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
		root.put("className", className);
		root.put("lowerName", lowerName);
		root.put("servicePackage", servicePackage);
		root.put("domainPackage", domainPackage);
		root.put("mapperPackage", mapperPackage);
		root.put("serviceImplPackage", serviceImplPackage);
		root.put("controllerPackage", controllerPackage);

		root.put("daoPackage", mapperPage);
		root.put("keyType", keyType);
		root.put("tableName", tableName);
		root.put("codeName", codeName);
		root.put("feilds", createBean.getBeanFeilds(tableName, className, false));
		// root.put("queryfeilds", createBean.getQueryBeanFeilds(tableName, className));
		root.put("tablesAsName", tablesAsName);
		root.put("columnDatas", columnDatas);
		// root.put("columnKeyDatas", columnKeyDatas);
		ColumnData keyColu = (ColumnData) columnKeyDatas.get(0);
		root.put("keyColu", keyColu.getColumnName()); // 可能会有多个主键，此处默认只有一个主键取出来用于数据库子增长
		// root.put("columnKeyParam", columnKeyParam);
		// root.put("columnKeyUseParam", columnKeyUseParam);
		// root.put("SQL", sqlMap);

		Configuration cfg = new Configuration();
		String templateBasePath = getClassPath() + PropertiesReaderUtil.getConfigInfo("templatePath");
		cfg.setDirectoryForTemplateLoading(new File(templateBasePath));
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		FreemarkerEngine.createFileByFTL(cfg, root, "controllerClass.ftl", pathSrc, controllerPath);
		FreemarkerEngine.createFileByFTL(cfg, root, "jspManager.ftl", pathSrc, jspManagerPath);
		FreemarkerEngine.createFileByFTL(cfg, root, "serviceClass.ftl", pathSrc, servicePath);
		FreemarkerEngine.createFileByFTL(cfg, root, "serviceImplClass.ftl", pathSrc, serviceImplPath);
		FreemarkerEngine.createFileByFTL(cfg, root, "domainClass.ftl", pathSrc, domainPath);
		FreemarkerEngine.createFileByFTL(cfg, root, "mapperClass.ftl", pathSrc, mapperPath);
		FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap.ftl", pathSrc, sqlMapperPath);

		log.debug("生成代码结束");
		System.out.println("生成代码结束");
	}
}
