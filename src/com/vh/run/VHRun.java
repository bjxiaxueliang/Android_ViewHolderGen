package com.vh.run;

import com.vh.run.config.Config;
import com.vh.run.extract.XMLFileListBean;
import com.vh.run.extract.XMLLayoutExtractor;
import com.vh.run.extract.XMLLayoutBean;
import com.vh.run.utils.JarToolUtil;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * ViewHolder生成工具
 */
public class VHRun {

	public static void main(String args[]) throws Exception {
		// swing
		run();
	}

	/**
	 * 开始生成ViewHolder
	 */
	public static void run() throws Exception {
		System.out.println("--- VHRun genPath ---");
		// ../Android_ViewHolderGen
		String jarParentPath = JarToolUtil.getJarDir();
		System.out.println("jarParentPath: " + jarParentPath);
		/**
		 * resources资源路径
		 */
		// ../Android_ViewHolderGen/xml
		String layoutxmlPath = new File(jarParentPath, "xml").getPath();
		System.out.println("layoutxmlPath: " + layoutxmlPath);
		/**
		 *
		 */
		// 生成的ViewHolder：路径
		// ../Android_ViewHolderGen
		String genPath = jarParentPath;
		System.out.println("genPath: " + genPath);

		/**
		 *
		 */
		// --------# 生成的ViewHolder：包名----------
		// com.vh.gen
		String genPkg = Config.GEN_PKG;
		System.out.println("genPkg: " + genPkg);
		// --------# 生成的ViewHolder：R文件包名--------
		// com.vh.gen
		String genR = Config.GEN_R;
		System.out.println("genR: " + genR);

		// ------------------Velocity-----------------
		Properties p = new Properties();
		p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, jarParentPath);
		Velocity.init(p);
		// 包名与R名的匹配
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("genpkg", genPkg); // com.vh.gen
		velocityContext.put("genr", genR); // com.vh.gen
		// 模版
		Template template = Velocity.getTemplate("tm/vh_template.vm");

		// ------------------获取所有layout文件-----------------
		XMLFileListBean xmlFlister = new XMLFileListBean();
		xmlFlister.setPath(layoutxmlPath); // ../Android_ViewHolderGen/xml
		ArrayList<String> layoutFiles = xmlFlister.mergeAndroidxmllist();
		System.out.println("layoutFiles: " + layoutFiles);
		// ------------------查找所有layout文件-----------------
		XMLLayoutExtractor extractor = new XMLLayoutExtractor();
		// 创建生成文件存放路径
		new File(genPath).mkdirs(); // ../Android_ViewHolderGen

		System.out.println("--- layout list ---");
		// 轮询所有文件
		for (Iterator iter = layoutFiles.iterator(); iter.hasNext();) {
			//
			String f = (String) iter.next();
			System.out.println("file: " + f);
			File file = new File(f);
			//
			XMLLayoutBean xmlLayoutBean = extractor.parseXML(new FileInputStream(file));
			xmlLayoutBean.setName(file.getName().replace(".xml", ""));
			// 添加替换项 xmllayout
			velocityContext.put("xmllayout", xmlLayoutBean);

			// 比较替换，生成文件
			FileWriter sw = new FileWriter(genPath + "/" + xmlLayoutBean.getFormalName() + ".java");
			template.merge(velocityContext, sw);
			sw.flush();
			sw.close();
		}

	}

}
