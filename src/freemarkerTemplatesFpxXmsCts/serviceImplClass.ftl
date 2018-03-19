package ${serviceImplPackage};

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qznet.iov.center.common.model.Users;
import com.qznet.iov.market.page.Page;

import ${servicePackage}.${className}Service;
import ${poPackage}.${className};
import ${mapperPackage}.${className}Mapper;

/**
 * <P>${codeName}</P>
 * 
 * @author ${author} ${nowDate}
 */
@Service("${lowerName}Service")
public class ${className}ServiceImpl implements ${className}Service {

private static final Logger logger = LoggerFactory.getLogger(${className}Service.class);

	@Autowired
	${className}Mapper ${lowerName}Mapper;
	
<#--
	/**
	 * <p>跟进查询条件查找对应记录</p>
	 * 
	 * @param paramsMap
	 * @return
	 * @author ${author} ${nowDate}
	 */
	${className} findByMap(Map<String, Object> paramsMap){
		${lowerName}Mapper.findByMap(paramsMap);
	}
-->

	/**
	 * 更新为发布下架状态或删除状态
	 * @param paramsMap
	 * @return 
	 * @author${author} ${nowDate}
	 */
	@Override
	@Transactional
	public int updateStatusOrDel(Map<String, Object> paramsMap) {

		int ret = 1;
		try {
			${lowerName}Mapper.updateStatusOrDel(paramsMap);
		} catch (Exception e) {
			ret = 0;
			logger.debug("操作数据时异常：  " + e.getMessage());
		}
		return ret;
	}

	/**
	 * 
	 * <p>查询广告列表页面</p>
	 * 
	 * @param paramsMap
	 * @return
	 * @author${author} ${nowDate}
	 */
	@Override
	public Page<${className}> listPage(Map<String, Object> paramsMap) {
		Page<${className}> page = new Page<${className}>(new ArrayList<${className}>(), 0);
		try {
			Integer count = ${lowerName}Mapper.selectCountByMap(paramsMap);

			if (count != null && count > 0) {
				paramsMap.put("start", Integer.parseInt((String) paramsMap.get("start")));
				paramsMap.put("limit", Integer.parseInt((String) paramsMap.get("limit")));
				List<${className}> list = ${lowerName}Mapper.selectListByMap(paramsMap);
				page = new Page<${className}>(list, count);
			}
		} catch (Exception e) {
			logger.debug("操作数据时异常：  " + e.getMessage());
		}
		return page;
	}

	/**
	 * <p>新增或修改</p>
	 * 
	 * @param ${lowerName}
	 * @param Users
	 * @return
	 * @author${author} ${nowDate}
	 */
	@Transactional
	@Override
	public int addOrupdate(${className} ${lowerName}, Users users) {

		int ret = 1;
		try {
			if (${lowerName}.getId() != null){
				${lowerName}.setIsDeleted(0);
				${lowerName}Mapper.update(${lowerName});
			}
			else {
				${lowerName}.setCompanyId(users.getCompanyId());
				${lowerName}.setCreatedBy(users.getRealName());
				${lowerName}.setCreatedTime(new Date());
				
				${lowerName}Mapper.insert(${lowerName});
			}

		} catch (Exception e) {
			ret = 0;
			logger.debug("操作数据时异常：  " + e.getMessage());
		}

		return ret;
	}
}
