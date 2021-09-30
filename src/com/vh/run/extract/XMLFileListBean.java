package com.vh.run.extract;

import com.vh.run.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * XML文件列表
 */
public class XMLFileListBean {
    private static final String TAG = "XMLFileListBean";
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
     * 
     * test_main_layout.xml
     * D:\WS_Studio\Android_ViewHolderGen\src\main\resources\xml\layout\test_main_layout.xml
     * 
     * test_title_layout.xml
     * D:\WS_Studio\Android_ViewHolderGen\src\main\resources\xml\layout\test_title_layout.xml
     *
     * @return
     */
    public ArrayList<String> mergeAndroidxmllist() {
        LogUtils.d(TAG,"--- mergeAndroidxmllist ---");
        File dir = new File(mPath);
        LogUtils.d(TAG,"dir: " + dir);
        if (dir.exists()) {
            LogUtils.d(TAG,"dir.exists(): " + dir.exists());
        }
        //
        if (!dir.isDirectory()) {
            LogUtils.d(TAG,"dir.isDirectory(): " + dir.isDirectory());
            return new ArrayList<String>();
        }
        //--------xml路径--------
        File[] flist = dir.listFiles();
        LogUtils.d(TAG,"flist: " + flist);
        for (int i = 0; i < flist.length; i++) {
            LogUtils.d(TAG,"i: " + i);
            File f = flist[i];
            LogUtils.d(TAG,"file : " + f);
            // 以xml结尾
            if (f.getName().endsWith("xml") && !result.containsKey(f.getName())) {
                // 以文件名为key 以path为value
                result.put(f.getName(), f.getAbsolutePath());
                //
                LogUtils.d(TAG,"name: " + f.getName());
                LogUtils.d(TAG,"path: " + f.getAbsolutePath());
            }
        }

//        //--------xml.layoutxxx路径--------
//        for (int i = 0; i < flist.length; i++) {
//            File f = flist[i];
//            if (f.getName().startsWith("layout"))
//                if (!f.getName().equals("layout")) {
//                    mergeDir(f);
//                }
//        }
        //--------返回value--------
        ArrayList<String> r = new ArrayList<String>();
        r.addAll(result.values());
        return r;
    }

    public void mergeDir(File dir) {
        LogUtils.d(TAG,"--- mergeDir ---");
        LogUtils.d(TAG,"dir: "+dir);
        File[] flist = dir.listFiles();
        LogUtils.d(TAG,"flist: "+flist);
        for (int i = 0; i < flist.length; i++) {
            LogUtils.d(TAG,"i: "+i);
            File f = flist[i];
            LogUtils.d(TAG,"file: "+f);
            // 以xml结尾
            if (f.getName().endsWith("xml") && !result.containsKey(f.getName())) {
                // 以文件名为key 以path为value
                result.put(f.getName(), f.getAbsolutePath());

                LogUtils.d(TAG,"path: " + f.getAbsolutePath());
            }
        }
    }
}
