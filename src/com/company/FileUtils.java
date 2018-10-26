package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.company
 * 2017/12/01
 * Created by LeoLiu on User.
 */

class FileUtils {
    private static final String VALUE_CONDITION = "\">(.*?)</string>";
    private static final String HTML_CONDITION = "TRANSLATED_TEXT='(.*?)'";

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * 向文件中写入内容
     *
     * @param file 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(File file, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp;

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 翻译
     *
     * @param sourceFilePath 需要翻译的文件路径
     * @param targetFilePath 翻译生成的文件路径
     * @param typeSource     需要翻译的语种
     * @param typeTarget     翻译的目标语种
     */
    public static void translate(File sourceFilePath, File targetFilePath, String typeSource, String typeTarget) {
        try {
            if (targetFilePath.exists()) {
                targetFilePath.delete();
            } else {
                targetFilePath.getParentFile().mkdirs();
            }
            targetFilePath.createNewFile();
        } catch (IOException e) {
            System.out.println("创建新文件异常  target>>>" + typeTarget + " " + Google.LANGUAGE.get(typeTarget) + "       " + e.getMessage());
            e.printStackTrace();
            return;
        }
        try {
            InputStreamReader rdCto = new InputStreamReader(new FileInputStream(sourceFilePath), "utf-8");
            BufferedReader bfReader = new BufferedReader(rdCto);
            String txtline;
            while ((txtline = bfReader.readLine()) != null) {
                // 过滤注释或者空行
                if (!txtline.contains("<string") || txtline.contains("<!--")) {
                    FileUtils.writeFileContent(targetFilePath, txtline);
                    continue;
                }
                String result = FileUtils.getSubUtilSimple(txtline, VALUE_CONDITION); // 获取要翻译的文字
                String html = Google.translate(result, typeSource, typeTarget);
                if (html == null) {
                    System.out.println("  ERROR 翻译失败 >>>>> language " + typeTarget + "   " + targetFilePath.getParentFile().getName());
                }

                String result1 = FileUtils.getSubUtilSimple(html, HTML_CONDITION);
                if (result1.length() == 0) {
                    System.out.println("  ERROR 翻译失败 >>>>> language " + typeTarget + "   " + targetFilePath.getParentFile().getName());
                    System.out.println("重新翻译 >>> language " + typeTarget);
                    addTranslateLanguage(typeSource, sourceFilePath.getPath(), typeTarget);
                    return;
                }

                // 过滤单引号乱码
                result1 = result1.replace("\\x26#39;", "\'");

                FileUtils.writeFileContent(targetFilePath, txtline.replace(result, result1));
            }
            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加翻译
     *
     * @param sourceType     需要翻译的语种
     * @param sourceFilePath 需要翻译的文件地址
     * @param languages      翻译的目标语种
     */
    public static void addTranslateLanguage(String sourceType, String sourceFilePath, String... languages) {
        for (String typeTarget : languages) {
            new Thread(new LanguageRunnable(sourceType, sourceFilePath, typeTarget)).start();
        }
    }
}
