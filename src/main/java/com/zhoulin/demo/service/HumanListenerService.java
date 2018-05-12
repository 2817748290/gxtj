package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.LogInfoDTO;

import java.util.HashMap;
import java.util.List;

public interface HumanListenerService {

    public HashMap<Integer, List<LogInfoDTO>> userReadTime(int userId) throws Exception;

}
