package com.zhoulin.demo;

import com.zhoulin.demo.bean.Info;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.mapper.InfoMapper;
import com.zhoulin.demo.mapper.InformationMapper;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.InfoService;
import com.zhoulin.demo.service.PushService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InfoServiceTest extends DemoApplicationTests {

    @Autowired
    private InfoService infoService;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private TypeRelationMapper typeRelationMapper;
    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private PushService pushService;

    @Test
    public void tyFindTest(){
        try {
           List<Information> informationList = informationMapper.findAll();
            for (Information information: informationList) {
                TypeRelation t = typeRelationMapper.getInfoByTRId(information.getId());
                System.out.println(t.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateFindTest(){

        try {
//            List<Info> infoList = infoService.findInfoByDate(0);
            List<Info> infoList = infoMapper.findInfoByDate(1);
            for (Info info: infoList) {
                System.out.println("!!!!" + info.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void forImageTest(){

        long id = 24473;

        try {
            Info info = infoService.getInfoByInfoId(id);
            System.out.println(info.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void isExistInfo(){

        try {
            List<Info> infos = pushService.pushInfoByUserGroup(1);
            System.out.println("count >>>" + infos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
