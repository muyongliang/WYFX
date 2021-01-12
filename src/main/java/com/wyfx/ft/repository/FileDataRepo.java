package com.wyfx.ft.repository;

import com.wyfx.ft.model.DataInfo;
import com.wyfx.ft.model.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by liu on 2017/8/9.
 */
public interface FileDataRepo extends BaseRepo<DataInfo> {
    Page<DataInfo> findByFileInfo(FileInfo fileInfo, Pageable page);


}
