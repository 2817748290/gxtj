# 针对资讯的用户建模和个性推荐系统-说明文档
> 创建时间：2018年3月5日13:50:56
>
> 最后更新：2018年5月12日14:39:49

## 项目说明
个性推荐

#### 一、项目构架

1.  服务端：Spring Boot + Mybatis + Mysql + Logback

    > logback（日志框架） 不提倡使用System.out打印
    
        private Logger logger =  LoggerFactory.getLogger(this.getClass());
        logger.info("占位 {} 自动填充 {} 可多个", a , b);
        
2.  搜索引擎：ElasticSearch + Kafka
    
    > ElasticSearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。
    
    > Elasticsearch是用Java开发的, 能够达到实时搜索，稳定，可靠，快速，安装使用方便。
    
    > resources/db 存放ElasticSearch索引 information_index_mapping.json
    
    > Kafka 在项目中作为消息中间使用。

3.  缓存机制：Redis集群(哨兵框架)
    
    > Windows版Redis
    
    > 启动Redis .\redis-server.exe .\redis.windows.conf
    
    > 启动哨兵模式 .\redis-server.exe .\sentinel.conf --sentinel 

4.  权限控制：Spring Security + JWT token
    
    > token值：登录成功后在返回，位于header.refresh
        
    > 访问需要权限的url，需在请求的header里带上 token:token值
        
    > token快过期的时候，会在返回头内header.refresh的值提供新的token值

5.  前端：Vue.js + Bootstrap + Axios + Element-UI

6.  训练算法：Word2vec
    
    > Word2vec是Google开源的一款用于词向量计算的工具
    
    > Word2vec Java版 https://github.com/NLPchina/Word2VEC_java
    
7.  训练语料：Sogou实验室 2012年全完新闻数据
    
    > http://www.sogou.com/labs/resource/list_news.php
    
8.  注意事项
    
    >* 整合HanLP中的朴素贝叶斯分类器
    >    
    >>    https://github.com/hankcs/HanLP
    >
    >>    hanlp.properties (放在 target/classes 和 target/test-classes)
    
    >* 整合Jcseg算法
    >
    >>    https://github.com/lionsoul2014/jcseg
    >
    >>    jcseg.properties 和 lexicon (放在 target/classes)
    
    >* 文件夹注意事项
    >
    >> properties文件夹存放 配置文件 以及 jcseg词典库
    >    
    >> data文件夹存放 训练语料 训练模型 字典库
    >
    >> sql文件夹存放 数据库sql
    >
    >> resources/iK 存放各种项目需要的jar包 必须导入

#### 二、返回示例
    接口返回数据 全部使用Message类封装
    
>   {"status":1（状态码）,"message":"返回成功","result":数据}

#### 三、工具类包

>* CommonUtil.java
>
>>      getProjectFilePath() : 获取项目资源文件储存路径
>>
>>      getIpAddr() ：获取访问者IP地址
>
>* UploadUtil.java
>
>>      singleFileUpload() : 上传文件
>
>* DownloadUtil.java
>
>>      downUploadFile() : 下载UploadUtil的上传文件
>>
>>      downFile() : 通用下载类
