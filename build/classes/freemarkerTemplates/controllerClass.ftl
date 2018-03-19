package ${controllerPackage};

import javax.servlet.http.HttpServletRequest;
import net.intelink.zmframework.model.*;
import net.intelink.zmframework.util.*;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.intelink.zmframework.base.web.BaseController;

import ${domainPackage}.${className};
import ${servicePackage}.${className}Service;

 /**
 * 描述：</b>${className}Controller :${codeName}<br>
 * @author ${author}
 * @since：${nowDate}
 * @version:1.0
 */
@Controller
@RequestMapping("/${lowerName}")
public class ${className}Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

	@Autowired
	private ${className}Service ${lowerName}Service;

	/**
	 * <p>新增或修改</p>
	 * 
	 * @param HttpServletRequest request
	 * @param ${className} ${lowerName}
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@RequestMapping("/addOrupdate/1.0")
	@ResponseBody
	public int addOrupdate(HttpServletRequest request, ${className} ${lowerName}) {
		return ${lowerName}Service.addOrupdate(${lowerName}, getUser(request));
	}

	
	/**
	 * <p>查询</p>
	 * 
	 * @param HttpServletRequest ${lowerName}
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@RequestMapping("/list/1.0")
	@ResponseBody
	public Page<${className}> list(HttpServletRequest request) {
		return ${lowerName}Service.listPage(new ControllerHellper().getRequestMap(request));
	}

}
