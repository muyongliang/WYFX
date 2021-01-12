package com.wyfx.ft.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/8/10.
 */
@Entity
@Table(name = "fw_type")
public class FwType extends BaseModel {
    /**
     * 文件类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
