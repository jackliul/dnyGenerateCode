package ${domainPackage};

import java.io.Serializable;
import java.util.Date;
import com.qznet.iov.center.common.model.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 描述：</b>${className}:${codeName}<br>
 * @author ${author}
 * @since：${nowDate}
 * @version:1.0
 */
@Data
public class ${className} extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	${feilds}
}

