package table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import util.PropertiesReaderUtil;


public class CreateBean {
	private Connection connection = null;
	static String url;
	static String username;
	static String password;
	static String rt = "\r\t";
	String SQLTables = "show tables";
	private String method;
	private String argv;
	static String selectStr = "select ";
	static String from = " from ";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMysqlInfo(String urla, String usernameb, String passwordc) {
		url = urla;
		username = usernameb;
		password = passwordc;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public List<String> getTables() throws SQLException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(this.SQLTables);
		ResultSet rs = ps.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			String tableName = rs.getString(1).toUpperCase();
			list.add(tableName);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}

	public List<TableInfo> getTablesInfo() throws SQLException {
		String SQLTables = "select t.TABLE_NAME ,t.TABLE_COMMENT from information_schema.tables  t  WHERE table_schema = '"
				+ PropertiesReaderUtil.getDATABASE_NAME() + "'";
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			TableInfo tableInfo = new TableInfo();
			String tableName = rs.getString(1);
			String tableComment = rs.getString(2);
			tableInfo.setTableName(tableName);
			tableInfo.setTableComment(tableComment);
			list.add(tableInfo);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}

	public Map<String, String> getTableCommentMap() throws SQLException {
		String SQLTables = "select t.TABLE_NAME ,t.TABLE_COMMENT from information_schema.tables  t  WHERE table_schema = '"
				+ PropertiesReaderUtil.getDATABASE_NAME() + "'";
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		Map map = new HashMap();
		while (rs.next()) {
			String tableName = rs.getString(1).toUpperCase();
			String tableComment = rs.getString(2);
			map.put(tableName, tableComment);
		}
		rs.close();
		ps.close();
		con.close();
		return map;
	}

	public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
		String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable,COLUMN_KEY from information_schema.columns where table_name =  '"
				+ tableName + "' "; // + "and table_schema =  '" + CodeResourceUtil.DATABASE_NAME +
									// "'";

		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List columnList = new ArrayList();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		while (rs.next()) {
			String name = rs.getString(1);
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
			String nullable = TableConvert.getNullAble(rs.getString(7));
			String columnKey = rs.getString(8) == null ? "" : rs.getString(8);
			String dataType = getType(type, precision, scale);
			String domainPropertyName = getcolumnNameToDomainPropertyName(name);
			String jdbcType = getJdbcType(type, precision, scale);

			ColumnData cd = new ColumnData();
			cd.setColumnName(name);
			cd.setDataType(dataType);
			cd.setJdbcType(jdbcType);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			cd.setDomainPropertyName(domainPropertyName);
			cd.setColumnKey(columnKey);
			columnList.add(cd);
		}
		this.argv = str.toString();
		this.method = getset.toString();
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	public List<ColumnData> getColumnKeyDatas(List<ColumnData> columnList) {
		List columnKeyList = new ArrayList();
		if ((columnList != null) && (columnList.size() > 0)) {
			for (ColumnData column : columnList) {
				if ("PRI".equals(column.getColumnKey())) {
					columnKeyList.add(column);
				}
			}
		}
		return columnKeyList;
	}

	public String getColumnKeyParam(List<ColumnData> columnList) {
		StringBuffer sb = new StringBuffer("");
		if ((columnList != null) && (columnList.size() > 0)) {
			for (ColumnData column : columnList) {
				sb.append(column.getDataType()).append(" ").append(column.getDomainPropertyName()).append(",");
			}
		}
		String str = sb.toString();
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public String getColumnKeyUseParam(List<ColumnData> columnList) {
		StringBuffer sb = new StringBuffer("");
		if ((columnList != null) && (columnList.size() > 0)) {
			for (ColumnData column : columnList) {
				sb.append(column.getDomainPropertyName()).append(",");
			}
		}
		String str = sb.toString();
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public String getBeanFeilds(String tableName, String className, boolean generateSwagger) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			String name = d.getDomainPropertyName();
			String type = d.getDataType();
			String comment = d.getColumnComment();

			String maxChar = name.substring(0, 1).toUpperCase();
			str.append("\r\t").append("/**");
			str.append("\r\t").append(" *").append(comment);
			str.append("\r\t").append(" */");
			str.append("\r\t");
			if ("Date".equals(type)) { // 如果是日期类型的话 自动加上spring日期类型注解
				str.append("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")").append("\r\t");
			}
			if (generateSwagger) { // 生成info类时，需要加入对swagger的支持
				str.append("@ApiModelProperty(required = true, value = ").append(" \" ").append(comment).append(" \" ")
						.append(")").append("\r\t");
			}

			str.append("private ").append(type + " ").append(name).append(";");
			String method = maxChar + name.substring(1, name.length()); // 因为会利用@Data注解进行操作，所以不用get set方法
			getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("    this." + name + "=").append(name).append(";\r\t}");
		}
		this.argv = str.toString();
		this.method = getset.toString();
		return this.argv + this.method;
	}

	public String getQueryBeanFeilds(String tableName, String className) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			String name = d.getDomainPropertyName();
			String type = d.getDataType();
			String comment = d.getColumnComment();

			String maxChar = name.substring(0, 1).toUpperCase();
			str.append("\r\t").append("/**");
			str.append("\r\t").append(" *").append(comment);
			str.append("\r\t").append(" */");
			if ("Date".equals(type)) { // 如果是日期类型的话 自动加上spring日期类型注解
				str.append("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")").append("\r\t");
			}
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";");
			String method = maxChar + name.substring(1, name.length());
			getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("    this." + name + "=").append(name).append(";\r\t}");
		}
		this.argv = str.toString();
		this.method = getset.toString();
		return this.argv + this.method;
	}

	public String getJdbcType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("int"))
			dataType = "INTEGER";
		else if (dataType.contains("date"))
			dataType = "TIMESTAMP";
		else if (dataType.contains("time"))
			dataType = "TIMESTAMP";
		else if (dataType.contains("clob"))
			dataType = "VARCHAR";
		else if (dataType.contains("text"))
			dataType = "VARCHAR";
		else {
			dataType = dataType.toUpperCase();
		}
		return dataType;
	}

	public String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "String";
		else if (dataType.contains("text"))
			dataType = "String";
		else if (dataType.contains("bigint"))
			dataType = "Long";
		else if (dataType.contains("int"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
				dataType = "BigDecimal";
			else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
				dataType = "Long";
			else
				dataType = "Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time"))
			dataType = "Date";
		else if (dataType.contains("clob"))
			dataType = "Clob";
		else {
			dataType = "Object";
		}
		return dataType;
	}

	public void getPackage(int type, String createPath, String content, String packageName, String className,
			String extendsClassName, String[] importName) throws Exception {
		if (packageName == null) {
			packageName = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; i++) {
			sb.append("import ").append(importName[i]).append(";\r");
		}
		sb.append("\r");
		sb.append("/**\r *  entity. @author wolf Date:"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null) {
			sb.append(" extends ").append(extendsClassName);
		}
		if (type == 1)
			sb.append(" ").append("implements java.io.Serializable {\r");
		else {
			sb.append(" {\r");
		}
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = temp + className.substring(1, className.length());
		if (type == 1) {
			sb.append("private " + className + " " + temp + "; // entity ");
		}
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		createFile(createPath, "", sb.toString());
	}

	public String getTablesNameToClassName(String tableName) {
		tableName = tableName.toLowerCase();
		String[] split = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < split.length; i++) { // TODO 为了去掉前面的数据库 前缀。
			for (int i = 1; i < split.length; i++) { 
				String tempTableName = split[i].substring(0, 1).toUpperCase()
						+ split[i].substring(1, split[i].length());
				sb.append(tempTableName);
			}
			return sb.toString();
		}
		String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
		return tempTables;
	}

	public String getTablesASName(String tableName) {
		tableName = tableName.toLowerCase();
		String[] split = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String tempTableName = split[i].substring(0, 1).toLowerCase();
				sb.append(tempTableName);
			}

			return sb.toString();
		}
		String tempTables = split[0].substring(0, 1).toLowerCase();
		return tempTables;
	}

	public String getcolumnNameToDomainPropertyName(String columnName) {
		columnName = columnName.toLowerCase();
		String[] split = columnName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String tempTableName = split[i].substring(0, 1).toUpperCase()
						+ split[i].substring(1, split[i].length());
				sb.append(tempTableName);
			}

			columnName = sb.toString();
			columnName = columnName.substring(0, 1).toLowerCase() + columnName.substring(1, columnName.length());
			return columnName;
		}
		columnName = split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length());
		return columnName;
	}

	public void createFile(String path, String fileName, String str) throws IOException {
		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}

	public Map<String, Object> getAutoCreateSql(String tableName) throws Exception {
		Map sqlMap = new HashMap();
		List columnDatas = getColumnDatas(tableName);
		String columns = getColumnSplit(columnDatas);
		String[] columnList = getColumnList(columns);
		String columnFields = getColumnFields(columns);
		String insert = "INSERT INTO " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n values(#{"
				+ columns.replaceAll("\\|", "},#{") + "})";
		String update = getUpdateSql(tableName, columnList);
		String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
		String selectById = getSelectByIdSql(tableName, columnList);
		String delete = getDeleteSql(tableName, columnList);
		sqlMap.put("columnList", columnList);
		sqlMap.put("columnFields", columnFields);
		sqlMap.put("insert", insert);
		sqlMap.put("update", update);
		sqlMap.put("delete", delete);
		sqlMap.put("updateSelective", updateSelective);
		sqlMap.put("selectById", selectById);
		return sqlMap;
	}

	public String getDeleteSql(String tableName, String[] columnsList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}

	public String getSelectByIdSql(String tableName, String[] columnsList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("select <include refid=\"Base_Column_List\" /> \n");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}

	public String getColumnFields(String columns) throws SQLException {
		String fields = columns;
		if ((fields != null) && (!"".equals(fields))) {
			fields = fields.replaceAll("[|]", ",");
		}
		return fields;
	}

	public String[] getColumnList(String columns) throws SQLException {
		String[] columnList = columns.split("[|]");
		return columnList;
	}

	public String getUpdateSql(String tableName, String[] columnsList) throws SQLException {
		StringBuffer sb = new StringBuffer();

		for (int i = 1; i < columnsList.length; i++) {
			String column = columnsList[i];
			if (!"CREATETIME".equals(column.toUpperCase())) {
				if ("UPDATETIME".equals(column.toUpperCase()))
					sb.append(column + "=now()");
				else {
					sb.append(column + "=#{" + column + "}");
				}
				if (i + 1 < columnsList.length)
					sb.append(",");
			}
		}
		String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{"
				+ columnsList[0] + "}";
		return update;
	}

	public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		ColumnData cd = columnList.get(0);
		sb.append("\t<trim  suffixOverrides=\",\" >\n");
		for (int i = 1; i < columnList.size(); i++) {
			ColumnData data = columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"").append(columnName).append(" != null ");

			if ("String" == data.getDataType()) {
				sb.append(" and ").append(columnName).append(" != ''");
			}
			sb.append(" \">\n\t\t");
			sb.append(columnName + "=#{" + columnName + "},\n");
			sb.append("\t</if>\n");
		}
		sb.append("\t</trim>");
		String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{"
				+ cd.getColumnName() + "}";
		return update;
	}

	public String getColumnSplit(List<ColumnData> columnList) throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		for (ColumnData data : columnList) {
			commonColumns.append(data.getColumnName() + "|");
		}
		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}

}
