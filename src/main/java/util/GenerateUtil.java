package util;

import generateCore.CodeGenerateFactoryIntelinkOms;
import generateCore.FtlDef;

/**
 * 描述：根据自定义表生成
 * 
 * @author：jacliu
 * @version:1.0
 */
public class GenerateUtil {

	/**
	 * <p>
	 * 根据模块名称生成 controller service jsp 模块
	 * </p>
	 * 
	 * @param args
	 * @author 刘利 2016年5月31日 上午9:39:35
	 */
	public static void main(String[] args) {
		try {
			String[] tables = NewPropertiesReaderUtil.getConfigInfo("tables").split(",");
			String[] infos = NewPropertiesReaderUtil.getConfigInfo("infos").split(",");
			for (int i = 0; i < tables.length; i++) {
				// CodeGenerateFactory.codeGenerateByFTL(tables[i], infos[i],
				// FtlDef.KEY_TYPE_02);
				CodeGenerateFactoryIntelinkOms.generateCode(tables[i], infos[i], FtlDef.KEY_TYPE_02);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
