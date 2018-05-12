package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.SearchLog;
import com.zhoulin.demo.service.SearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/searchLog")
public class SearchLogController {

    @Autowired
    private SearchLogService searchLogService;

    /**
     * 获取搜索热词
     * @return
     */
    @RequestMapping(value = "/getSearchWords", method = RequestMethod.POST)
    @ResponseBody
    public Message getSearchWords(){

        List<String> searchWords = new ArrayList<>();

        try {
            searchWords = searchLogService.getNowSearchCount();

            return new Message(Message.SUCCESS, "获取搜索热词>>>成功", searchWords);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "获取实时搜索热词>>>异常", e.getMessage());
        }

    }

    /**
     * 添加搜索日志
     */
    @RequestMapping(value = "/addSearchLog", method = RequestMethod.POST)
    @ResponseBody
    public Message addSearchLog(@RequestBody SearchLog searchLog){
        Integer addStatus = 0;
        try {
            addStatus = searchLogService.addSearchCount(searchLog);
            if (addStatus == 1){
                return new Message(Message.SUCCESS, "添加搜索记录>>>成功", addStatus);
            }
            return new Message(Message.FAILURE, "添加搜索记录>>>失败", addStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "添加搜索记录>>>异常", e.getMessage());
        }

    }

}
