package com.wyfx.ft.repository;


import com.wyfx.ft.model.FwFileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by liu on 2017/8/18.
 */
public interface FwFileInfoRepo extends BaseRepo<FwFileInfo> {
    Page<FwFileInfo> findByStatus(int yes, Pageable page);
}
