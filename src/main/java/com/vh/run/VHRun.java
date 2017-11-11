package com.vh.run;

import com.vh.run.extract.XMLLayoutExtractor;
import com.vh.run.extract.XMLLayoutLister;
import com.vh.run.extract.XMLFileLister;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * ViewHolder生成工具
 */
public class VHRun {

    /**
     * 程序入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 读取 config.properties 配置
        HashMap<String, String> properties = parseInputStream(new FileInputStream("src/main/resources/config.properties"));
        //--------# 生成的ViewHolder：路径----------
        //gen.path=src\main\java\com\vh\gen
        String genPath = properties.get("gen.path");
        //--------# 生成的ViewHolder：包名----------
        //gen.pkg=src.main.java.com.vh.gen
        String genPkg = properties.get("gen.pkg");
        //--------# 生成的ViewHolder：R文件包名--------
        //gen.r=com.jiangtai.djx
        String genR = properties.get("gen.r");
        //--------# resources资源路径--------
        //layoutxml.path=src\main\resources\xml
        String layoutxmlPath = properties.get("layoutxml.path");


        //------------------Velocity-----------------
        Velocity.init();
        // 包名与R名的匹配
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("genpkg", genPkg);
        velocityContext.put("genr", genR);
        // 模版
        Template template = Velocity.getTemplate("src/main/java/com/vh/run/template/vh_template.vm");

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

    /**
     * 读取文件，以键值对方式返回呈现
     *
     * @param input
     * @return
     * @throws IOException
     */
    private static HashMap<String, String> parseInputStream(InputStream input) throws IOException {
        HashMap<String, String> ret = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String l = null;
        while (null != (l = br.readLine())) {
            String[] ss = l.split("=");
            if (ss != null && ss.length > 1) {
                ret.put(ss[0].trim(), ss[1].trim());
            }

        }
        return ret;
    }
}
