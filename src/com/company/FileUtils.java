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

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static synchronized String getSubUtilSimple(String soap, String rgex) {
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
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static synchronized boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
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

    public static void newFile(String oldFilePath, String newFilePath, String now, String target) {
        File ctoFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        try {
            if (newFile.exists()) {
                System.out.println("文件已经存在>>>" + newFilePath);
                newFile.delete();
            }
            newFile.createNewFile();
        } catch (IOException e) {
            System.out.println("创建新文件异常>>>" + e.getMessage());
            e.printStackTrace();
            return;
        }
        System.out.println("  start  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> language " + target + "   " + newFile.getName());
        try {
            InputStreamReader rdCto = new InputStreamReader(new FileInputStream(ctoFile), "utf-8");
            BufferedReader bfReader = new BufferedReader(rdCto);
            String txtline = null;
            while ((txtline = bfReader.readLine()) != null) {
                System.out.println(" query  txtline>>>> " + txtline);

                String result = FileUtils.getSubUtilSimple(txtline, Main.VALUE_CONDITION); // 获取要翻译的文字

                String html = Google.translate(result, now, target);

                if (html == null) {
                    System.out.println(txtline + "-----翻译失败");
                }

                String result1 = FileUtils.getSubUtilSimple(html, Main.HTML_CONDITION);

                FileUtils.writeFileContent(newFilePath, txtline.replace(result, result1));
            }
            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("  end  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> language " + target + "   " + newFile.getName());
        }
    }
}
