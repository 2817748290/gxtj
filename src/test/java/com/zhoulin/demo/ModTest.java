package com.zhoulin.demo;

import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.service.InformationService;
import com.zhoulin.demo.service.ModService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ModTest extends DemoApplicationTests {

    @Autowired
    private ModService modService;

    @Autowired
    private InformationService informationService;

    @Test
    public void modForInfo(){

        List<Information> informationList = new ArrayList<>();

        String type = "财经";

        String finalType = "";

        try {
            informationList = informationService.findAll();
            for (Information information:informationList) {
//                finalType = modService.modForInfoType(information.getId(), type);
                System.out.println("类型>>>>>>>>>>>" + finalType);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void queryMutiAndTypeNameTest(){

        InfoSearch infoSearch = new InfoSearch("舒淇","文化");

        ServiceMultiResult<Long> multiResult = modService.queryMutiAndTypeName(infoSearch);

        System.out.println("queryMutiAndTypeName >>>> " + multiResult.getResult().size() + " size " + multiResult.getTotal());

        ServiceMultiResult<Long> multiResult1 = modService.queryMuti(infoSearch);

        System.out.println("queryMuti >>>> " + multiResult1.getResult().size() + " size " + multiResult1.getTotal());

        ServiceMultiResult<Long> multiResult2 = modService.queryTypeName(infoSearch);

        System.out.println("queryTypeName >>>> " + multiResult2.getResult() + " size " + multiResult2.getTotal());
    }

}
