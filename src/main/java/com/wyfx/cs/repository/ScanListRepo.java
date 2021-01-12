package com.wyfx.cs.repository;

import com.wyfx.cs.model.ScanList;

/**
 * Created by liu on 2017/12/7.
 */
public interface ScanListRepo extends BaseRepo<ScanList> {
    ScanList findByProjectKey(String projectKey);
}
