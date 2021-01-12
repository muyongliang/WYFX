package com.wyfx.cs.model.vo;

/**
 * Created by liu on 2018/1/3.
 */
public class IssuesVo extends BaseVo {
    /**
     * 漏洞名称
     */
    private String message;
    /**
     * 危险级别
     */
    private String severity;
    /**
     * 文件
     */
    private String component;
    /**
     * 类型
     */
    private String type;
    /**
     * 所在行数
     */
    private int line;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
