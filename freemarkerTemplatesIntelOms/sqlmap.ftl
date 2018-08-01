<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${daoPackage}.I${className}Dao">
	
 	<resultMap id="${lowerName}" type="${poPackage}.${className}PO">
				<id column="id" property="id" />
		<#list columnDatas as item>
			<#if item.columnKey != 'PRI'>
				<result column="${item.columnName}" property="${item.domainPropertyName}" />
			</#if>
	   </#list>
	</resultMap>
	
  <!-- 查询条件 -->
  <sql id="wherecontation">
    <trim  suffixOverrides="," >
       <#list columnDatas as item>
        <#if item.columnKey != 'PRI'>
         <if test="${item.domainPropertyName} != null and ${item.domainPropertyName} != ''" >
             /* ${item.columnComment} */
            AND ${tablesAsName}.${item.columnName} =  ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcType?upper_case}}
         </if>
        </#if>
      </#list>
    </trim>
  </sql>
	
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
	
<!--
  方法名称: insert
  调用路径: ${poPackage}.${className}.insert
  开发信息: 
  处理信息: 保存信息
  -->
  <insert id="insert" parameterType="Object" useGeneratedKeys="true" keyProperty="${keyColu}">
    INSERT  INTO  ${tableName}   /* ${codeName} */  
          (  
          <#list columnDatas as item>
            <#assign x="${item.columnName?length}" /> 
            <#if item_index==0>
${"                      "}${item.columnName}${"                              "?substring(item.columnName?length)}/* ${item.columnComment} */ 
            <#elseif item_index gt 0>
${"                     "},${item.columnName}${"                              "?substring(item.columnName?length)}/* ${item.columnComment} */ 
            </#if>
          </#list>     
          )
      values (
          <#list columnDatas as item>
            <#if item_index==0>
${"                      "}${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcType?upper_case}}${"                              "?substring(item.domainPropertyName?length)}/* ${item.columnComment} */ 
            <#elseif item_index gt 0>
${"                     "},${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcType?upper_case}}${"                              "?substring(item.domainPropertyName?length)}/* ${item.columnComment} */ 
            </#if>
          </#list>
          )
  </insert>

  
  <!--
  方法名称: update
  调用路径: ${poPackage}.${className}.update
  开发信息: 
  处理信息: 修改信息
  -->  
   <update id="update" parameterType="Object" >
    UPDATE   ${tableName}    /* ${codeName} */
          <trim   prefix="SET" suffixOverrides="," >
            <#list columnDatas as item>
            <#if item.columnKey !='PRI' >
             <if test="${item.domainPropertyName} != null">
                 /* ${item.columnComment} */ 
                 ${item.columnName} = ${"#"}{${item.domainPropertyName},jdbcType=${item.jdbcType?upper_case}},
             </if>
            </#if>
          </#list>
            </trim>
        WHERE
                id = ${"#"+"{id}"}    /* 序号 */ 
   </update>
	
</mapper>