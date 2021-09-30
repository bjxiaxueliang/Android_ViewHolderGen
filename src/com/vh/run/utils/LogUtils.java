package com.vh.run.utils;

public class LogUtils {

    private static final String DEFAULT_TAG = "xiaxl";


    public static void d(String tag, String s) {
        System.out.println(getMsg(tag, s));
    }


    // #################################################


    /**
     * @param msg
     * @return
     */
    private static String getMsg(String tag, String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(DEFAULT_TAG);
        if (tag != null && !tag.isEmpty()) {
            sb.append("_");
            sb.append(tag);
        }
        sb.append(": ");
        sb.append(msg);
        return sb.toString();
    }

}
