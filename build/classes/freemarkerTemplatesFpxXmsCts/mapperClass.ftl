package ${mapperPackage};

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import ${poPackage}.${className};

/**
 * 描述：</b>${className}Mapper :${codeName}<br>
 * @author：${author}
 * @since：${nowDate}
 * @version:1.0
 */
@Repository("${lowerName}Mapper")
public interface ${className}Mapper{
	<#--
	${className} findByMap(Map<String, Object> paramsMap);
	-->
	void updateStatusOrDel(Map<String, Object> paramsMap);

	Integer selectCountByMap(Map<String, Object> paramsMap);

	List<${className}> selectListByMap(Map<String, Object> paramsMap);

	void update(${className} ${lowerName});

	void insert(${className} ${lowerName});
}

