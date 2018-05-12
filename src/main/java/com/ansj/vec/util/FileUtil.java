package com.ansj.vec.util;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 12:40 2018/3/16
 */
public class FileUtil {
    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static String readTxtFile(String filePath){
        String result = "";
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    result += lineTxt;
                }
                System.out.println("读取txt文件完成");
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 保存文本文件
     * @param content
     * @param filename
     */
    public static void saveTxtFile(String content,String filename){
        try {
            File file = new File("E:\\sougoudata\\convert\\"+filename);
            if(!file.exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(file,false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close(); fw.close();
            System.out.println("txt文件保存成功 done!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将html转换成文本
     * @param inputString
     * @return
     */
    public static String Html2Txt(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {System.err.println("Html2Text: " + e.getMessage()); }
        //剔除空格行
        textStr=textStr.replaceAll("[ ]+", " ");
        textStr=textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        System.out.println("html2txt转换成功");
        textStr = filterNumAndLetter(textStr);
        return textStr;// 返回文本字符串
    }

    /**
     * 过滤数字和字母
     * @param input
     * @return
     */
    public static String filterNumAndLetter(String input){
        input = input.replaceAll("[(0-9)]","");
        input = input.replaceAll("[(A-Za-z)]","");
        System.out.println(input);
        return input;
    }
    public static void main(String argv[]){
//        String filePath = "C:\\Users\\84972\\Desktop\\gxtj\\src\\main\\java\\com\\ansj\\vec\\3.txt";
////      "res/";
//        readTxtFile(filePath);
        String s = Html2Txt(readTxtFile("C:\\Users\\84972\\Desktop\\gxtj\\src\\main\\java\\com\\ansj\\vec\\demo.txt"));
//        saveTxtFile(s);
        System.out.println(s);

//        filterNumAndLetter("cccc的发生31232的");
    }
}
