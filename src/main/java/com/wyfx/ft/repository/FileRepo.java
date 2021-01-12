package com.wyfx.ft.repository;

import com.alibaba.fastjson.JSONArray;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.model.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.17:12
 * description:
 * version:
 */
public interface FileRepo extends BaseRepo<FileInfo> {
    Page<FileInfo> findByStatus(int yesFileSign, Pageable page);

    FileInfo findById(Long fileId);

    FileInfo findByUuid(String uuid);

    List<FileInfo> findByIdIn(JSONArray jsonArray);

    List<FileInfo> findByFileType(FileType fileType);

    List<FileInfo> findByStatus(int yes);

    Page<FileInfo> findByStatusOrFireWallNumber(int yes, boolean b, Specification<FileInfo> specification, Pageable page);
}
