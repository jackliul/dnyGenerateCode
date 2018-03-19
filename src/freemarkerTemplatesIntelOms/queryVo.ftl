package ${queryVoPackage};

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 描述：</b>${className}:${codeName}<br>
 * @author ${author}
 * @since：${nowDate}
 * @version:1.0
 */
//@Data
public class ${className}QueryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	${feilds}
	
	private int start;
	
	private int pageSize;
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}

