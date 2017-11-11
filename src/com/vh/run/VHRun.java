package com.vh.run;

import com.vh.run.config.Config;
import com.vh.run.extract.XMLFileLister;
import com.vh.run.extract.XMLLayoutExtractor;
import com.vh.run.extract.XMLLayoutLister;
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


//    public static void main(String args[]) throws Exception {
//        // swing
//        run();
//    }

    /**
     * 开始生成ViewHolder
     */
    public static void run() throws Exception {

        String jarParentPath = JarToolUtil.getJarDir();
        /**
         * resources资源路径
         */
        //layoutxml.path=src\main\resources\xml
        //String layoutxmlPath = Config.LAYOUT_XML;
        String layoutxmlPath = new File(jarParentPath, "xml").getPath();
        /**
         *
         */
        // 生成的ViewHolder：路径
        //gen.path=src\main\java\com\vh\gen
        String genPath = jarParentPath;


        /**
         *
         */
        //--------# 生成的ViewHolder：包名----------
        //gen.pkg=src.main.java.com.vh.gen
        String genPkg = Config.GEN_PKG;
        //--------# 生成的ViewHolder：R文件包名--------
        //gen.r=com.jiangtai.djx
        String genR = Config.GEN_R;


        //------------------Velocity-----------------
        Properties p = new Properties();
        p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, jarParentPath);
        Velocity.init(p);
        // 包名与R名的匹配
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("genpkg", genPkg);
        velocityContext.put("genr", genR);
        // 模版
        Template template = Velocity.getTemplate("tm/vh_template.vm");

        //------------------获取所有layout文件-----------------
        XMLFileLister xmlFlister = new XMLFileLister();
        xmlFlister.setPath(layoutxmlPath);
        ArrayList<String> layoutFiles = xmlFlister.mergeAndroidxmllist();

        //------------------查找所有layout文件-----------------
        XMLLayoutExtractor extractor = new XMLLayoutExtractor();
        // 创建生成文件存放路径
        new File(genPath).mkdirs();
        // 轮询所有文件
        for (Iterator iter = layoutFiles.iterator(); iter.hasNext(); ) {
            //
            String f = (String) iter.next();
            File file = new File(f);
            //
            XMLLayoutLister xmlLayoutList = extractor.parseXML(new FileInputStream(file));
            xmlLayoutList.setName(file.getName().replace(".xml", ""));
            // 添加替换项 xmllayout
            velocityContext.put("xmllayout", xmlLayoutList);

            // 比较替换，生成文件
            FileWriter sw = new FileWriter(genPath + "/VH_" + xmlLayoutList.getName() + ".java");
            template.merge(velocityContext, sw);
            sw.flush();
            sw.close();
        }

    }


}
