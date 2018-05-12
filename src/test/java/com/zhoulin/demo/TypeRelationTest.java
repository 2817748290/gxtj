package com.zhoulin.demo;

import com.zhoulin.demo.bean.Info;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.InfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TypeRelationTest extends DemoApplicationTests {

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private InfoService infoService;

    @Test
    public void addPublishDate(){

        TypeRelation typeRelation = new TypeRelation();

        try {
            List<Info> list = infoService.findAll();
            Integer status = 0;
            for (Info info:list) {
                typeRelation = typeRelationMapper.getInfoByTRId(info.getInfoId());
                typeRelation.setPublishDate(info.getPublishDate());
                status = typeRelationMapper.updateTypeRelationByInfoId(typeRelation);
                System.out.println("!!!!" + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
