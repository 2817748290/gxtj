package com.zhoulin.demo.service.search;

/**
 * Kafka消息结构体
 */
public class InformationIndexMessage {

    public static final String INDEX = "index";

    public static final String REMOVE = "remove";

    public static final Integer MAX_RETRY = 3;

    private Long infoId;

    private String operation;

    private Integer retry = 0;

    /**
     * 默认构造器 防止jackson序列失败
     */
    public InformationIndexMessage() {
    }

    public InformationIndexMessage(Long infoId, String operation, Integer retry) {
        this.infoId = infoId;
        this.operation = operation;
        this.retry = retry;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }
}
