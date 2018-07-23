package table;

public class ColumnData {

	public static final String OPTION_REQUIRED = "required:true";
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	private String columnName;
	private String domainPropertyName;
	private String dataType;
	private String columnComment;
	private String columnType;
	private String jdbcType;
	private String columnKey;
	private String charmaxLength = "";
	private String nullable;
	private String scale;
	private String precision;

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return this.columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getScale() {
		return this.scale;
	}

	public String getPrecision() {
		return this.precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getCharmaxLength() {
		return this.charmaxLength;
	}

	public String getNullable() {
		return this.nullable;
	}

	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getDomainPropertyName() {
		return this.domainPropertyName;
	}

	public void setDomainPropertyName(String domainPropertyName) {
		this.domainPropertyName = domainPropertyName;
	}

	public String getColumnKey() {
		return this.columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getJdbcType() {
		return this.jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

}
