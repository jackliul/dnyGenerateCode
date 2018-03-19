<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${daoPackage}.I${className}Dao">
	
	<!--
	方法名称: count
	调用路径: ${poPackage}.${className}.count
	开发信息: 
	处理信息: 列表总数
	-->
	<select id="count" resultType="java.lang.Integer"  parameterType="java.util.Map">
		SELECT count(*)  FROM  ${tableName}      AS ${tablesAsName}      /* ${codeName} */ 
		 WHERE 1 = 1
		 	and is_deleted = 0
		    <include refid="wherecontation"/>
	</select>
  	
  	<!--
	方法名称: list
	调用路径: ${poPackage}.${className}.list
	开发信息: 
	处理信息: 列表
	-->
	<select id="list" parameterType="java.util.Map"  resultMap="${lowerName}">
		    SELECT 
				  <#list columnDatas as item>
					   <#if item_index==0>
${"                   "}${tablesAsName}.${item.columnName}${"                              "?substring(item.columnName?length)}/* ${item.columnComment} */ 
					   <#else>
${"                  "},${tablesAsName}.${item.columnName}${"                              "?substring(item.columnName?length)}/* ${item.columnComment} */ 
						 </#if>
				   </#list> 
		FROM   	 ${tableName}      AS ${tablesAsName}      /* ${codeName} */ 
		WHERE 1 = 1
			  and is_deleted = 0
		   <include refid="wherecontation"/>
		LIMIT  ${"#"+"{start}"}  		/* 开始序号 */ 
			  ,${"#"+"{limit}"}		/* 每页显示条数 */
	</select>
	
</mapper>