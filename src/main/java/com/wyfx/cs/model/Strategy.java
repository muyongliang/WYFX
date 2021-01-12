package com.wyfx.cs.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liu on 2017/12/12.
 * 策略
 */
@Entity
@Table(name = "cs_strategy")
public class Strategy extends BaseModel {
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "strategy")
    Set<ScanList> scanLists = new HashSet<>();
    /**
     * 名称
     */
    private String name;

    public Set<ScanList> getScanLists() {
        return scanLists;
    }

    public void setScanLists(Set<ScanList> scanLists) {
        this.scanLists = scanLists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
