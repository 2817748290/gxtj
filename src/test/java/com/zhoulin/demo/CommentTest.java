package com.zhoulin.demo;

import com.zhoulin.demo.bean.InfoComment;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.service.CommentService;
import com.zhoulin.demo.service.InformationService;
import com.zhoulin.demo.service.ModService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CommentTest extends DemoApplicationTests {

    @Autowired
    private CommentService commentService;

    @Test
    public void get(){

        try {
            List<InfoComment> comments = commentService.getMostLikesComments();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
