package com.vh.run;

import com.vh.run.extract.XMLFileListBean;
import com.vh.run.extract.XMLLayoutExtractor;
import com.vh.run.extract.XMLLayoutBean;
import com.vh.run.utils.ConfigUtil;
import com.vh.run.utils.JarToolUtil;
import com.vh.run.utils.LogUtils;

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
public class VhRunner {
    private static final String TAG = "VhRunner";


    public static void main(String args[]) throws Exception {
        // swing
        run();
    }

    /**
     * 开始生成ViewHolder
     */
    public static void run() throws Exception {
        LogUtils.d(TAG,"--- VHRun genPath ---");
        // ../Android_ViewHolderGen
        String rootPathDir = JarToolUtil.getJarDir();
        LogUtils.d(TAG,"rootPathDir: " + rootPathDir);
        /**
         * resources资源路径
         */
        // ../Android_ViewHolderGen/xml_tm/xml
        String xmlPathDir = new File(rootPathDir, "xml_tm/xml").getPath();
        LogUtils.d(TAG,"xmlPathDir: " + xmlPathDir);
        /**
         *
         */
        // 生成的ViewHolder：路径
        // ../Android_ViewHolderGen
        String genVhPathDir = rootPathDir;
        LogUtils.d(TAG,"genVhPathDir: " + genVhPathDir);

        /**
         *
         */
        // --------# 生成的ViewHolder：包名----------
        // com.vh.gen
        String genVhPkg = ConfigUtil.GEN_PKG;
        LogUtils.d(TAG,"genVhPkg: " + genVhPkg);
        // --------# 生成的ViewHolder：R文件包名--------
        // com.vh.gen
        String genVhR = ConfigUtil.GEN_R;
        LogUtils.d(TAG,"genR: " + genVhR);

        // ------------------Velocity-----------------
        Properties p = new Properties();
        p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, rootPathDir);
        Velocity.init(p);
        // 包名与R名的匹配
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("genpkg", genVhPkg); // com.vh.gen
        velocityContext.put("genr", genVhR); // com.vh.gen
        // 模版
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        Template template = ve.getTemplate("xml_tm/tm/vh_template.vm");

        // ------------------获取所有layout文件-----------------
        XMLFileListBean xmlFlister = new XMLFileListBean();
        xmlFlister.setPath(xmlPathDir); // ../Android_ViewHolderGen/xml
        ArrayList<String> layoutFiles = xmlFlister.mergeAndroidxmllist();
        LogUtils.d(TAG,"layoutFiles: " + layoutFiles);
        // ------------------查找所有layout文件-----------------
        XMLLayoutExtractor extractor = new XMLLayoutExtractor();
        // 创建生成文件存放路径
        File genVhFileDir = new File(genVhPathDir);
        if (!genVhFileDir.exists()) {
            genVhFileDir.mkdirs(); // ../Android_ViewHolderGen
        }
        LogUtils.d(TAG,"--- layout list ---");
        // 轮询所有文件
        for (Iterator iter = layoutFiles.iterator(); iter.hasNext(); ) {
            //
            String f = (String) iter.next();
            LogUtils.d(TAG,"file: " + f);
            File file = new File(f);
            //
            XMLLayoutBean xmlLayoutBean = extractor.parseXML(new FileInputStream(file));
            xmlLayoutBean.setName(file.getName().replace(".xml", ""));
            // 添加替换项 xmllayout
            velocityContext.put("xmllayout", xmlLayoutBean);

            // 比较替换，生成文件
            FileWriter sw = new FileWriter(genVhPathDir + "/" + xmlLayoutBean.getFormalName() + ".java");
            template.merge(velocityContext, sw);
            sw.flush();
            sw.close();
        }

    }

}
