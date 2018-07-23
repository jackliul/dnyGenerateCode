package generateCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import util.NewPropertiesReaderUtil;


public class FreemarkerEngine {
	private static final Log log = LogFactory.getLog(FreemarkerEngine.class);

	public static void createFileByFTL(Configuration cfg, Map<String, Object> root, String ftlName, String fileDirPath,
			String targetFile) {
		String encoding = NewPropertiesReaderUtil.getSYSTEM_ENCODING();
		String workspasePath = NewPropertiesReaderUtil.getConfigInfo("workspacePath");
		boolean isReplace = new Boolean(NewPropertiesReaderUtil.getConfigInfo("isReplace")).booleanValue();
		try {
			Template temp = cfg.getTemplate(ftlName, encoding);
			File file = new File(workspasePath + File.separator + fileDirPath + File.separator + targetFile);
			if (!file.exists()) {
				new File(file.getParent()).mkdirs();
				log.debug("创建文件:" + file.getAbsolutePath());
				System.out.println("创建文件:" + file.getAbsolutePath());
			} else if (isReplace) {
				log.debug("创建文件:" + file.getAbsolutePath());
				System.out.println("替换文件:" + file.getAbsolutePath());
			}

			FileOutputStream os = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(os, encoding);
			temp.process(root, out);
			out.flush();
		} catch (IOException e) {
			log.debug(e.getMessage());
			System.out.println(e.getMessage());
		} catch (TemplateException e) {
			log.debug(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
}
