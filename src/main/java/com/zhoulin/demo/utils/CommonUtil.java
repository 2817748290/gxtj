package com.zhoulin.demo.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by lzp20 on 2017/4/29.
 */
@Component
public class  CommonUtil {

    /**
     * 获取application配置的项目文件储存目录
     */
    public static String WINDOWS_PATH;
    public static String LINUX_PATH;

    @Value("${com.liangliang.custom.windows-path}")
    public void setWindowsPath(String path) {
        WINDOWS_PATH = path;
    }

    @Value("${com.liangliang.custom.linux-path}")
    public void setLinuxPath(String path) {
        LINUX_PATH = path;
    }

    /**
     * 返回项目的文件存储目录
     * @return
     */
	public static String getProjectFilePath() {
        String path = null;
        if(System.getProperty("os.name").indexOf("Windows") != -1) {
            path = WINDOWS_PATH;
        }else {
            path = LINUX_PATH;
        }
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            System.out.println("创建文件夹================");
        }
        return path;
    }
    /**
     * 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
    public static String setContentType(String returnFileName){
        String contentType = "application/octet-stream";
        if (returnFileName.lastIndexOf(".") < 0)
            return contentType;
        returnFileName = returnFileName.toLowerCase();
        returnFileName = returnFileName.substring(returnFileName.lastIndexOf(".")+1);

        if (returnFileName.equals("html") || returnFileName.equals("htm") || returnFileName.equals("shtml")){
            contentType = "text/html";
        } else if (returnFileName.equals("css")){
            contentType = "text/css";
        } else if (returnFileName.equals("xml")){
            contentType = "text/xml";
        } else if (returnFileName.equals("gif")){
            contentType = "image/gif";
        } else if (returnFileName.equals("jpeg") || returnFileName.equals("jpg")){
            contentType = "image/jpeg";
        } else if (returnFileName.equals("js")){
            contentType = "application/x-javascript";
        } else if (returnFileName.equals("atom")){
            contentType = "application/atom+xml";
        } else if (returnFileName.equals("rss")){
            contentType = "application/rss+xml";
        } else if (returnFileName.equals("mml")){
            contentType = "text/mathml";
        } else if (returnFileName.equals("txt")){
            contentType = "text/plain";
        } else if (returnFileName.equals("jad")){
            contentType = "text/vnd.sun.j2me.app-descriptor";
        } else if (returnFileName.equals("wml")){
            contentType = "text/vnd.wap.wml";
        } else if (returnFileName.equals("htc")){
            contentType = "text/x-component";
        } else if (returnFileName.equals("png")){
            contentType = "image/png";
        } else if (returnFileName.equals("tif") || returnFileName.equals("tiff")){
            contentType = "image/tiff";
        } else if (returnFileName.equals("wbmp")){
            contentType = "image/vnd.wap.wbmp";
        } else if (returnFileName.equals("ico")){
            contentType = "image/x-icon";
        } else if (returnFileName.equals("jng")){
            contentType = "image/x-jng";
        } else if (returnFileName.equals("bmp")){
            contentType = "image/x-ms-bmp";
        } else if (returnFileName.equals("svg")){
            contentType = "image/svg+xml";
        } else if (returnFileName.equals("jar") || returnFileName.equals("var") || returnFileName.equals("ear")){
            contentType = "application/java-archive";
        } else if (returnFileName.equals("doc")){
            contentType = "application/msword";
        } else if (returnFileName.equals("pdf")){
            contentType = "application/pdf";
        } else if (returnFileName.equals("rtf")){
            contentType = "application/rtf";
        } else if (returnFileName.equals("xls")){
            contentType = "application/vnd.ms-excel";
        } else if (returnFileName.equals("ppt")){
            contentType = "application/vnd.ms-powerpoint";
        } else if (returnFileName.equals("7z")){
            contentType = "application/x-7z-compressed";
        } else if (returnFileName.equals("rar")){
            contentType = "application/x-rar-compressed";
        } else if (returnFileName.equals("swf")){
            contentType = "application/x-shockwave-flash";
        } else if (returnFileName.equals("rpm")){
            contentType = "application/x-redhat-package-manager";
        } else if (returnFileName.equals("der") || returnFileName.equals("pem") || returnFileName.equals("crt")){
            contentType = "application/x-x509-ca-cert";
        } else if (returnFileName.equals("xhtml")){
            contentType = "application/xhtml+xml";
        } else if (returnFileName.equals("zip")){
            contentType = "application/zip";
        } else if (returnFileName.equals("mid") || returnFileName.equals("midi") || returnFileName.equals("kar")){
            contentType = "audio/midi";
        } else if (returnFileName.equals("mp3")){
            contentType = "audio/mpeg";
        } else if (returnFileName.equals("ogg")){
            contentType = "audio/ogg";
        } else if (returnFileName.equals("m4a")){
            contentType = "audio/x-m4a";
        } else if (returnFileName.equals("ra")){
            contentType = "audio/x-realaudio";
        } else if (returnFileName.equals("3gpp") || returnFileName.equals("3gp")){
            contentType = "video/3gpp";
        } else if (returnFileName.equals("mp4") ){
            contentType = "video/mp4";
        } else if (returnFileName.equals("mpeg") || returnFileName.equals("mpg") ){
            contentType = "video/mpeg";
        } else if (returnFileName.equals("mov")){
            contentType = "video/quicktime";
        } else if (returnFileName.equals("flv")){
            contentType = "video/x-flv";
        } else if (returnFileName.equals("m4v")){
            contentType = "video/x-m4v";
        } else if (returnFileName.equals("mng")){
            contentType = "video/x-mng";
        } else if (returnFileName.equals("asx") || returnFileName.equals("asf")){
            contentType = "video/x-ms-asf";
        } else if (returnFileName.equals("wmv")){
            contentType = "video/x-ms-wmv";
        } else if (returnFileName.equals("avi")){
            contentType = "video/x-msvideo";
        }

        return contentType;
    }

}
