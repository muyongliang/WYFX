package com.wyfx.cs.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liu on 2017/12/7.
 * 项目
 */
@Entity
@Table(name = "cs_item")
public class Item extends BaseModel {
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "item")
    Set<ScanList> scanLists = new HashSet<>();
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 描述
     */
    private String description;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ScanList> getScanLists() {
        return scanLists;
    }

    public void setScanLists(Set<ScanList> scanLists) {
        this.scanLists = scanLists;
    }
}
