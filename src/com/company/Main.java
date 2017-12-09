package com.company;

import java.io.File;

public class Main {
    public static final String VALUE_CONDITION = "\">(.*?)</string>";
    public static final String HTML_CONDITION = "TRANSLATED_TEXT='(.*?)'";

    // 模板语言文件
    private static final String oldFile = "E:\\IDEAWorks\\TranslateLanguage\\src\\com\\company\\language\\zhLanguage";
    // 模板语言
    private static final String Current_Language = "中文简体";

    public static void main(String[] args) {
        // 需要翻译的语言
        addNewLanguage("英语", "俄语", "法语","西班牙语","葡萄牙语","泰语","阿拉伯语","波斯语");
    }


    public static void addNewLanguage(String... languages) {
        for (String language : languages) {
            new Thread(new LanguageRunnable(oldFile, new File(oldFile).getParent() + "\\" + Google.LANGUAGE.get(language), Current_Language, language)).start();
        }
    }
}
