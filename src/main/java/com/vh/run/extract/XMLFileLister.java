package com.vh.run.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * XML文件列表
 */
public class XMLFileLister {
    /**
     *
     */
    private String mPath;

    /**
     * 返回layout文件路径
     *
     * @return
     */
    public String getPath() {
        return mPath;
    }

    /**
     * 设置layout文件路径
     *
     * @param path
     */
    public void setPath(String path) {
        this.mPath = path;
    }

    /**
     * 以文件名为key 以path为value(去重用)
     */
    private HashMap<String, String> result = new HashMap<String, String>();


    /**
     * 以列表的形式返回文件路径，例如：
     * D:\WS_Studio\Android_ViewHolderGen\src\main\resources\xml\layout\test_main_layout.xml
     * D:\WS_Studio\Android_ViewHolderGen\src\main\resources\xml\layout\test_title_layout.xml
     *
     * @return
     */
    public ArrayList<String> mergeAndroidxmllist() {
        File dir = new File(mPath);
        //
        if (!dir.isDirectory()) {
            return new ArrayList<String>();
        }
        //--------xml.layout路径--------
        File[] flist = dir.listFiles();
        for (int i = 0; i < flist.length; i++) {
            File f = flist[i];
            // 找到xml.layout路径
            if (f.getName().equals("layout")) {
                mergeDir(f);
            }
        }

        //--------xml.layoutxxx路径--------
        for (int i = 0; i < flist.length; i++) {
            File f = flist[i];
            if (f.getName().startsWith("layout"))
                if (!f.getName().equals("layout")) {
                    mergeDir(f);
                }
        }
        //--------返回value--------
        ArrayList<String> r = new ArrayList<String>();
        r.addAll(result.values());
        return r;
    }

    public void mergeDir(File dir) {
        File[] flist = dir.listFiles();
        for (int i = 0; i < flist.length; i++) {
            File f = flist[i];
            // 以xml结尾
            if (f.getName().endsWith("xml") && !result.containsKey(f.getName())) {
                // 以文件名为key 以path为value
                result.put(f.getName(), f.getAbsolutePath());

                System.out.println("path: " + f.getAbsolutePath());
            }
        }
    }
}
