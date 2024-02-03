package com.tlmall.open.domain.hisrequest.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * @author loulan
 * @desc 历史处理日志
 */
public class HisRequestLog {

    private String transIdLogFilePath;
    private String transIdLogFileName;
    private String transIdLogFileContent;

    public HisRequestLog(String transIdLogFilePath, String transIdLogFileName) {
        this.transIdLogFilePath = transIdLogFilePath;
        this.transIdLogFileName = transIdLogFileName;
    }

    public HisRequestLog(String transIdLogFilePath, String transIdLogFileName, String transIdLogFileContent) {
        this.transIdLogFilePath = transIdLogFilePath;
        this.transIdLogFileName = transIdLogFileName;
        this.transIdLogFileContent = transIdLogFileContent;
    }

    public String getTransIdLogFilePath() {
        return transIdLogFilePath;
    }

    public void setTransIdLogFilePath(String transIdLogFilePath) {
        this.transIdLogFilePath = transIdLogFilePath;
    }

    public String getTransIdLogFileName() {
        return transIdLogFileName;
    }

    public void setTransIdLogFileName(String transIdLogFileName) {
        this.transIdLogFileName = transIdLogFileName;
    }

    public String getTransIdLogFileContent() {
        return transIdLogFileContent;
    }

    public void setTransIdLogFileContent(String transIdLogFileContent) {
        this.transIdLogFileContent = transIdLogFileContent;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
