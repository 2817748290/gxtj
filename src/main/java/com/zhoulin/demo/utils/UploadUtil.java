package com.zhoulin.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lzp20 on 2017/4/29.
 */

public class UploadUtil {

    private static Logger logger = LoggerFactory.getLogger("upload");

    /**
     * 上传文件
     * @param multipartFile conroller层接收的
     * @param saveDirName 保存的文件夹名称
     * @param ip ip记录（非必需）
     * @return map:{ status : true(false),
     *              path : 存储的文件相对路径+文件名
     *          }
     */
    public static Map<String, Object> singleFileUpload(MultipartFile multipartFile, String saveDirName, String ip) {
        String fileName = null;
        String saveDirName2 = CommonUtil.getProjectFilePath() + "/upload/" + saveDirName + "/" + getDays();
        File folder = new File(saveDirName2);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", false);
        if (!multipartFile.isEmpty()) {
            fileName = multipartFile.getOriginalFilename();
            fileName = getFileNameToAvoidRepeat(folder, fileName);
            map.put("path", saveDirName + "/" + getDays() + "/" + fileName);
            map.put("status", true);
            /** 拼成完整的文件保存路径文件 **/
            fileName = saveDirName2 + "/" + fileName;

            logger.info("ip:{} ;upload-save-Path:{}" ,ip , fileName);
            File file = new File(fileName);
            try {
                multipartFile.transferTo(file);
            } catch (IllegalStateException e) {
                map.put("status", false);
                e.printStackTrace();
            } catch (IOException e) {
                map.put("status", false);
                e.printStackTrace();
            }
        }
        return map;

    }



    /**
     * 避免上传文件将同名文件覆盖，发现同名文件时，将文件名前加上时间戳
     *
     * @param file     :目录
     * @param fileName :上传文件的文件名
     * @return
     */
    public static String getFileNameToAvoidRepeat(File file, String fileName) {
        UUID uuid = UUID.randomUUID();

        fileName = uuid.toString() +"."+ fileName.substring(fileName.lastIndexOf(".")+1);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    if (f.getName().equals(fileName)) {
                        fileName = "(" + getDays() + ")" + fileName;
                        break;
                    }
                }
            }
        }
        return fileName;

    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");

    private static String getDays() {
        return simpleDateFormat.format(new Date());
    }
}
