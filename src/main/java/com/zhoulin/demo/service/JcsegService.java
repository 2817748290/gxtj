package com.zhoulin.demo.service;

import java.util.List;

public interface JcsegService {

    public List<String> getKeyphrase(String content) throws Exception;

    public List<String> getKeywordsphrase(String content) throws Exception;

}
