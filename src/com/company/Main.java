package com.company;

import java.io.File;

public class Main {
    public static final String VALUE_CONDITION = "\">(.*?)</string>";
    public static final String HTML_CONDITION = "TRANSLATED_TEXT='(.*?)'";

    // 模板语言文件
    private static final String oldFile = "src\\com\\company\\language\\zhLanguage";
    // 模板语言
    private static final String Current_Language = "中文简体";

    public static void main(String[] args) {
        // 需要翻译的语言
        addNewLanguage(
                "英语",
                "俄语",
                "西班牙语",
                "德语",
                "意大利语",
                "法语",
                "葡萄牙语",
                "波兰语",
                "荷兰语",
                "希伯来语",
                "波斯语",
                "希腊语",
                "瑞典语",
                "捷克语",
                "阿拉伯语",
                "马来语",
                "印地语",
                "乌尔都语"
        );
    }


    public static void addNewLanguage(String... languages) {
        for (String language : languages) {
            File file = new File(oldFile);
            File file1 = new File(file.getParent() + File.separator + "values-" + Google.LANGUAGE.get(language));
            file1.mkdirs();
            new Thread(new LanguageRunnable(oldFile, file1.toString() + File.separator + "strings.xml", Current_Language, language)).start();
        }
    }
}
