package ${daoPackage};

import java.util.List;
import java.util.Map;

import net.intelink.zmframework.base.dao.IBaseDAO;
import net.intelink.zmframework.exception.BaseException;

import ${poPackage}.${poName};

/**
 * <P>${codeName}</P>
 * 
 * @author ${author} ${nowDate}
 */
public interface I${className}Dao  extends IBaseDAO<${poName}, Integer>{
	/**
	 * <p>根据条件查找列表总条数</p>
	 * 
	 * @param Map<String,Object> paramsMap
	 * @return List<${className}>
	 * @author ${author} ${nowDate}
	 */
	Integer count(Map<String,Object> paramsMap) throws BaseException;
	
	
	/**
	 * <p>根据条件查找列表数据</p>
	 * 
	 * @param Map<String,Object> paramsMap
	 * @return List<${className}>
	 * @author ${author} ${nowDate}
	 */
	List<${poName}> list(Map<String,Object> paramsMap) throws BaseException;
	
}
