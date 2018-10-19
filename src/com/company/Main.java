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

       "阿尔巴尼亚语",
       "阿拉伯语",
       "阿塞拜疆语",
       "爱尔兰语",
       "爱沙尼亚语",
       "巴斯克语",
       "白俄罗斯语",
       "保加利亚语",
       "冰岛语",
       "波兰语",
       "波斯语",
       "布尔语",
       "南非荷兰语",
       "丹麦语",
       "德语",
       "俄语",
       "法语",
       "菲律宾语",
       "芬兰语",
       "格鲁吉亚语",
       "古吉拉特语",
       "海地克里奥尔语",
       "韩语",
       "荷兰语",
       "加利西亚语",
       "加泰罗尼亚语",
       "捷克语",
       "卡纳达语",
       "克罗地亚语",
       "拉丁语",
       "拉脱维亚语",
       "老挝语",
       "立陶宛语",
       "罗马尼亚语",
       "马耳他语",
       "马来语",
       "马其顿语",
       "孟加拉语",
       "挪威语",
       "葡萄牙语",
       "日语",
       "瑞典语",
       "塞尔维亚语",
       "世界语",
       "斯洛伐克语",
       "斯洛文尼亚语",
       "斯瓦希里语",
       "泰卢固语",
       "泰米尔语",
       "泰语",
       "土耳其语",
       "威尔士语",
       "乌尔都语",
       "乌克兰语",
       "希伯来语",
       "希腊语",
       "西班牙语",
       "匈牙利语",
       "亚美尼亚语",
       "意大利语",
       "意第绪语",
       "印地语",
       "印尼语",
       "英语",
       "越南语",
       "中文繁体",
       "中文简体"
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
