package com.zhoulin.demo;

import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.mapper.LogInfoMapper;
import com.zhoulin.demo.mapper.UserInfoMapper;
import com.zhoulin.demo.service.PushUserGroupService;
import com.zhoulin.demo.service.SpiderService;
import com.zhoulin.demo.service.impl.ModServiceImpl;
import com.zhoulin.demo.utils.ReckonUserGroup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class SpiderTest extends DemoApplicationTests{
    private static final Logger logger = LoggerFactory.getLogger(ModServiceImpl.class);

//  private InformationService informationService;
//    @Autowired
//    private SpiderService spiderService;

    @Autowired
    private SpiderService newSpiderService;

    @Autowired
    private ReckonUserGroup reckonUserGroup;

    @Autowired
    private PushUserGroupService pushUserGroupService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private LogInfoMapper logInfoMapper;

    @Test
    public void test() {
//        for(int i=1;i<=5;i++){
            newSpiderService.run();

//            System.out.println("爬虫执行完一次。。。。。");
//
//            newSpiderService.run();
//        }


    }

    @Test
    public void testArea(){

        List<Integer> integerlist = reckonUserGroup.reckonTypeArea(1);

        System.out.println(integerlist);

    }

    @Test
    public void rc() throws Exception {

//        UserInfo userInfo = userInfoMapper.findUserPW("zhoulin", "2817748290@qq.com");

        ;

        System.out.println("!!!!!!" + logInfoMapper.isReadInfo(1,1L));

    }



}
