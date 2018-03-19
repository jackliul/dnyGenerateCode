package ${controllerPackage};

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.intelink.zmframework.model.*;
import net.intelink.zmframework.util.*;
import net.intelink.zmframework.base.web.BaseController;
import net.intelink.zmframework.component.ICache;
import net.intelink.zmframework.component.ISession;
import net.intelink.zmframework.enums.EnumResCode;
import net.intelink.zmframework.exception.ControllerException;
import net.intelink.zmframework.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import ${infoVoPackage}.${infoVoName};
import ${queryVoPackage}.${queryVoName};
import ${servicePackage}.I${className}Service;

 /**
 * 描述：</b>${className}Controller :${codeName}<br>
 * @author ${author}
 * @since：${nowDate}
 * @version:1.0
 */
@Api(value = "${codeName}接口", tags = { "${codeName}操作接口" })
@Controller
@RequestMapping("/${lowerName}")
public class ${className}Controller extends BaseController {

	@Autowired
	private I${className}Service ${lowerName}Service;

	/**
	 * <p>新增或修改</p>
	 * 
	 * @param HttpServletRequest request
	 * @param ${className} ${lowerName}
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@ApiResponses(value = { @ApiResponse(response = ${infoVoName}.class, code = 200, message = "返回成功"),
			@ApiResponse(code = 405, message = "请求参数有误"), @ApiResponse(code = 500, message = "服务器报错") })
	@ApiOperation(value = "addOrUpdate", httpMethod = "POST", notes = "新增或修改")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "100L", dataType = "Long", paramType = "query", example = "100") })
	@RequestMapping("/addOrupdate/1.0")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultModel<${infoVoName}> addOrUpdate(@ApiParam(required = true, name = "id", value = "id") String head, String body) throws Exception{
		
		RequestModel<${queryVoName}> requestModel = RequestUtil.getRequestModel(head, body, ${queryVoName}.class);
		// 1.获取请求参数实体
		${queryVoName} ${lowerQueryVoName} = null;
		try {
			${lowerQueryVoName} = requestModel.getBody();
			${lowerName}Service.addOrUpdate(${lowerQueryVoName});
		} catch (NullPointerException e) {
		
			LogHelper logHelper = new LogHelper(${infoVoName}.class);
			logHelper.debug4Task("${lowerInfoVoName}", "操作失败");
		
			return RespUtil.result(EnumResCode.INVALID_REQ_ENTITY, null);
		}
		
		return RespUtil.success();
	}

	
	/**
	 * <p>列表查询</p>
	 * 
	 * @param HttpServletRequest ${lowerName}
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@ApiResponses(value = {
		@ApiResponse(responseContainer = "List", response = ${infoVoName}.class, code = 200, message = "服务正常"),
		@ApiResponse(code = 405, message = "请求参数有误"), @ApiResponse(code = 500, message = "服务器报错"),
		@ApiResponse(code = 600, message = "自定义异常") })
	@ApiOperation(value = "list", httpMethod = "GET", notes = "列表查询")
	@RequestMapping("/list/1.0")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultModel<List<${infoVoName}>> list(String head, String body) throws Exception{
	   
	   	RequestModel<${queryVoName}> requestModel = RequestUtil.getRequestModel(head, body, ${queryVoName}.class);
		// 1.获取请求参数实体
		try {
			PageResult<${infoVoName}> ${lowerQueryVoName}Page = ${lowerName}Service.listPage(requestModel);
			
			return RespUtil.result(EnumResCode.OK, ${lowerQueryVoName}Page);
		} catch (NullPointerException e) {
			return RespUtil.result(EnumResCode.INVALID_REQ_ENTITY, null);
		}
	}

}
