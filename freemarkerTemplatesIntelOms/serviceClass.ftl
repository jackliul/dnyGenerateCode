package ${servicePackage};

import java.util.Map;
import java.util.List;

import net.intelink.zmframework.base.service.IBaseService;
import net.intelink.zmframework.model.PageResult;
import net.intelink.zmframework.model.RequestModel;

import net.intelink.zmframework.exception.BaseException;

import ${poPackage}.${poName};
import ${infoVoPackage}.${infoVoName};
import ${queryVoPackage}.${queryVoName};

/**
 * <P>${codeName}</P>
 * 
 * @author ${author} ${nowDate}
 */
public interface I${className}Service extends IBaseService<${poName}, Integer>{

	/**
	 * <p>查找分页列表</p>
	 * 
	 * @param ${queryVoName} ${lowerQueryVoName}
	 * @return PageResult
	 * @author ${author} ${nowDate}
	 */
	public PageResult<${infoVoName}> listPage(RequestModel<${queryVoName}> requestModel) throws BaseException;
	
	/**
	 * <p>根据条件查找列表数据</p>
	 * 
	 * @param ${queryVoName} ${lowerQueryVoName}
	 * @return List<${infoVoName}>
	 * @author ${author} ${nowDate}
	 */
	List<${infoVoName}> list(RequestModel<${queryVoName}> requestModel) throws BaseException;
	
	/**
	 * <p>新增或修改数据</p>
	 * 
	 * @param ${queryVoName} ${lowerQueryVoName}
	 * @return ${queryVoName}
	 * @author ${author} ${nowDate}
	 */
	${infoVoName} addOrUpdate(${queryVoName} ${lowerQueryVoName}) throws BaseException;

}

