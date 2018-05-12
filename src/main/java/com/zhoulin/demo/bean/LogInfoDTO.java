package com.zhoulin.demo.bean;

import java.io.Serializable;

public class LogInfoDTO implements Serializable{

    private LogInfo logInfo;

    private int typeId;

    private long timeDifference;

    //时间阈值定义为30s
    private long threshold = 30000;

    public LogInfoDTO() {
    }

    public LogInfoDTO(LogInfo logInfo, int typeId) {
        this.logInfo = logInfo;
        this.typeId = typeId;
    }

    public LogInfoDTO(LogInfo logInfo, int typeId, long timeDifference) {
        this.logInfo = logInfo;
        this.typeId = typeId;
        this.timeDifference = timeDifference;
    }

    public long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getThreshold() {
        return threshold;
    }

    @Override
    public String toString() {
        return "LogInfoDTO{" +
                "logInfo=" + logInfo +
                ", typeId=" + typeId +
                ", timeDifference=" + timeDifference +
                ", threshold=" + threshold +
                '}';
    }
}
