package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.InfoImage;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.mapper.InfoImageMapper;
import com.zhoulin.demo.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private InfoImageMapper infoImageMapper;

    /**
     * 新闻搜索接口 为搜索栏提供
     * @param searchContent
     * @return
     */
    @RequestMapping(value = "/getInfoBySearchBar", method = RequestMethod.POST)
    @ResponseBody
    public Message getInfoBySearchBar(@RequestParam("searchContent") String searchContent){

        InfoSearch infoSearch = new InfoSearch();

        InfoImage infoImage = new InfoImage();

        List<Information> informationList = new ArrayList<>();

        infoSearch.setMutiContent(searchContent);

        try {
            ServiceMultiResult<Information> informationServiceMultiResult = searchService.queryMultiMatch(infoSearch);

            informationList = informationServiceMultiResult.getResult();

            for (Information information : informationList) {
                infoImage = infoImageMapper.getInfoImageByInfoId(information.getId());
                information.setInfoImage(infoImage);
            }
            return new Message(Message.SUCCESS, "搜索>>>>>>成功！", informationList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR, "搜索>>>>>异常", null);
        }

    }

}
