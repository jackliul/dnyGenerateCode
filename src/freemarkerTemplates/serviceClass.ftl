package ${servicePackage};

import java.util.Map;

import com.qznet.iov.center.common.model.Users;
import com.qznet.iov.market.page.Page;

import ${domainPackage}.${className};

/**
 * <P>${codeName}</P>
 * 
 * @author ${author} ${nowDate}
 */
public interface ${className}Service {
	<#--
	/**
	 * <p>跟进查询条件查找对应记录</p>
	 * 
	 * @param paramsMap
	 * @return
	 * @author ${author} ${nowDate}
	 */
	${className} findByMap(Map<String, Object> paramsMap);
	-->
	/**
	 * <p>删除 发布 下架 记录</p>
	 * 
	 * @param paramsMap
	 * @return
	 * @author ${author} ${nowDate}
	 */
	public int updateStatusOrDel(Map<String, Object> paramsMap);

	/**
	 * <p>查找网页记录</p>
	 * 
	 * @param paramsMap
	 * @return
	 * @author ${author} ${nowDate}
	 */
	Page<${className}> listPage(Map<String, Object> paramsMap);

	/**
	 * <p>新增或修改</p>
	 * 
	 * @param ${lowerName}
	 * @param users
	 * @return
	 * @author ${author} ${nowDate}
	 */
	public int addOrupdate(${className} ${lowerName}, Users users);
}

