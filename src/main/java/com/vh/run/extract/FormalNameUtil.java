package com.vh.run.extract;

/**
 * 字符串替换和首字母大写
 */
public class FormalNameUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(replaceUnderlineAndfirstToUpper("ni_hao_abc", "_", ""));
    }

    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     *
     * @param srcStr 原始字符串
     * @param org    要替换的字符串
     * @param ob     替换后的字符串
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
        String newString = "";
        int first = 0;
        // 循环查找“org”
        while (srcStr.indexOf(org) != -1) {
            //
            first = srcStr.indexOf(org);

            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr.substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return firstCharacterToUpper(newString);
    }

    /**
     * 把"_"替换为"",并"_"后的首字母大写
     *
     * @param name
     * @return
     */
    public static String formalize(String name) {
        return replaceUnderlineAndfirstToUpper(name, "_", "");
    }
}
