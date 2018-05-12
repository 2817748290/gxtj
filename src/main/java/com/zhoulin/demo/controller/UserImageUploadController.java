package com.zhoulin.demo.controller;


import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.utils.CommonUtil;
import com.zhoulin.demo.utils.DownloadUtil;
import com.zhoulin.demo.utils.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户头像上传 下载
 */
@Controller
public class UserImageUploadController {

    @RequestMapping(value = "/api/user/userImageUpload", method = RequestMethod.POST )
    @ResponseBody
    public Message upload(@RequestParam MultipartFile file) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (file.isEmpty()) {
            return new Message(Message.ERROR,"文件为空",null);
        }else{
            String save = "userImage";

            Map map = UploadUtil.singleFileUpload(file,save, CommonUtil.getIpAddr(request));
            if((Boolean) map.get("status")){
                return new Message(Message.SUCCESS,"保存成功", "/public/download?filename="+map.get("path"));
            }else{
                return new Message(Message.ERROR,"保存失败",null);
            }
        }
    }


    @RequestMapping(value = "/public/download", method = RequestMethod.GET)
    @ResponseBody
    public void downFile(
            @RequestParam(value = "filename") String odexName,
            HttpServletResponse response,
            HttpServletRequest request) {
        DownloadUtil.downUploadFile(odexName,request,response);
    }

}
