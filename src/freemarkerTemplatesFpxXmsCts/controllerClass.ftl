package ${controllerPackage};

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qznet.iov.center.facade.controller.BaseController;
import com.qznet.iov.market.page.Page;
import com.qznet.iov.market.web.controller.common.ControllerHellper;

import ${poPackage}.${className};
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
	private I${className}Service ${lowerName}Service;

	/**
	 * <p>新增或修改</p>
	 * 
	 * @param request
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@RequestMapping("/addOrupdate.json")
	@ResponseBody
	public int addOrupdate(HttpServletRequest request, ${className} ${lowerName}) {
		return ${lowerName}Service.addOrupdate(${lowerName}, getUser(request));
	}

	
	/**
	 * <p>查询</p>
	 * 
	 * @param request
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@RequestMapping("/list.json")
	@ResponseBody
	public Page<${className}> list(HttpServletRequest request) {
		return ${lowerName}Service.listPage(new ControllerHellper().getRequestMap(request));
	}

	 /**
	 * <p>删除 发布 下架 记录</p>
	 * 
	 * @param request
	 * @return
	 * @author ${author} ${nowDate}
	 */
	@RequestMapping("/updateStatusOrDel.json")
	@ResponseBody
	public int updateStatusOrDel(HttpServletRequest request) {
		return  ${lowerName}Service.updateStatusOrDel(new ControllerHellper().getRequestMap(request));
	}

}
