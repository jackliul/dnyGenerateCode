package ${serviceImplPackage};

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${servicePackage}.I${className}Service;

import ${poPackage}.${poName};
import ${infoVoPackage}.${infoVoName};
import ${queryVoPackage}.${queryVoName};
import ${mapperPackage}.I${className}Dao;

import net.intelink.zmframework.model.RequestModel;
import net.intelink.zmframework.base.dao.IBaseDAO;
import net.intelink.zmframework.base.service.IBaseService;
import net.intelink.zmframework.base.service.AbstractService;
import net.intelink.zmframework.model.Limit;
import net.intelink.zmframework.model.PageResult;
import net.intelink.zmframework.exception.BaseException;

/**
 * <P>${codeName}</P>
 * 
 * @author ${author} ${nowDate}
 */
@Service("${lowerName}Service")
public class ${className}ServiceImpl extends AbstractService<${poName}, Integer> implements I${className}Service {

	@Autowired
	I${className}Dao ${lowerName}Dao;
	
	@Override
	public IBaseDAO<${poName}, Integer> getBaseDao() {
		return ${lowerName}Dao;
	}
	
	/**
	 * 
	 * <p>查询条件查找分页数据</p>
	 * 
	 * @param ${queryVoName} ${lowerQueryVoName}
	 * @return
	 * @author${author} ${nowDate}
	 */
	@Override
	public PageResult<${infoVoName}> listPage(RequestModel<${queryVoName}> requestModel) throws BaseException {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Integer total = ${lowerName}Dao.count(paramsMap); // TODO 加入实际业务代码
		Limit limit = null; 
		List<${infoVoName}> list = null;
		PageResult<${infoVoName}> page = null;
		
		${queryVoName} ${lowerQueryVoName} = requestModel.getBody();
		if(0 == total || null == total){
			page = new PageResult<${infoVoName}>(total,limit, new ArrayList<${infoVoName}>());
		}
		
		if (null != total && total > 0) {
			limit = Limit.buildLimit(${lowerQueryVoName}.getStart(),${lowerQueryVoName}.getPageSize());
			paramsMap.put("start", limit.getStart());
			paramsMap.put("pageSize", limit.getSize());
			list = list(requestModel);
			page = new PageResult<${infoVoName}>(total,limit, list);
			
		}
		
		
		return page;
	}
	
	/**
	 * <p>根据条件查找列表数据</p>
	 * 
	 * @param ${queryVoName} ${lowerQueryVoName}
	 * @return List<${infoVoName}>
	 * @author ${author} ${nowDate}
	 */
	@Override
	public List<${infoVoName}> list(RequestModel<${queryVoName}> requestModel) throws BaseException{
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		//TODO 加入实际业务参数
		List<${poName}> ${lowerPoName}list = ${lowerName}Dao.list(paramsMap);
		
		List<${infoVoName}> ${lowerInfoVoName}List = null;
		if (null != ${lowerPoName}list) {
			${lowerInfoVoName}List = new ArrayList<${infoVoName}>();
			for (${poName} ${lowerPoName} : ${lowerPoName}list) {
				${infoVoName} ${lowerInfoVoName} = new ${infoVoName}();
				BeanUtils.copyProperties(${lowerPoName}, ${lowerInfoVoName}, "");
				
				${lowerInfoVoName}List.add(${lowerInfoVoName});
			}
		}
		
		return ${lowerInfoVoName}List;
	}
	
	/**
	 * <p>新增或修改数据</p>
	 * 
	 * @param ${queryVoName} ${lowerQueryVoName}
	 * @return ${queryVoName}
	 * @author ${author} ${nowDate}
	 */
	@Override
	public ${infoVoName} addOrUpdate(${queryVoName} ${lowerQueryVoName}) throws BaseException{
	
		Integer ${lowerName}Id = ${lowerQueryVoName}.getId();
		${poName} ${lowerPoName} = null;
		if(null == ${lowerPoName}.getId()){
			${lowerPoName} = new ${poName}();
			BeanUtils.copyProperties(${lowerQueryVoName}, ${lowerPoName}, "");// TODO 业务代码还需确认
			super.insert(${lowerPoName});
		}
		
		if(null != ${lowerPoName}.getId()){
			${lowerPoName} = super.findById(${lowerPoName}.getId());
			BeanUtils.copyProperties(${lowerQueryVoName}, ${lowerPoName}, ""); // TODO 业务代码还需确认
			super.update(${lowerPoName});
		}
		
		${infoVoName} ${lowerInfoVoName} = new ${infoVoName}();
		BeanUtils.copyProperties(${lowerPoName}, ${lowerInfoVoName}, ""); 
		return ${lowerInfoVoName};
	}
}
