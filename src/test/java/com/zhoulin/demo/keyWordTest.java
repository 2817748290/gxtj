package com.zhoulin.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.HanLP;
import com.zhoulin.demo.bean.Info;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.InformationService;
import com.zhoulin.demo.service.JcsegService;
import com.zhoulin.demo.service.ModService;
import org.elasticsearch.search.SearchHit;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class keyWordTest extends DemoApplicationTests{


    @Autowired
    private JcsegService jcsegService;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFineGrain(){

        try {
            for(int i=1;i<11;i++){
                List<TypeRelation> typeRelations = typeRelationMapper.getInfoByTypeId(i);
                String content = "";
                for (TypeRelation typeRelation:typeRelations) {
                    content = content + typeRelation.getOnlyText();
                }
//                List<String> fineGrain = HanLP.extractPhrase(content,5);
                System.out.println(content);
                List<String> fineGrain = jcsegService.getKeyphrase(content);
                System.out.println(fineGrain.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
