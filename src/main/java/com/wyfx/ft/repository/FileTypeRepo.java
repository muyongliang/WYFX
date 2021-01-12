package com.wyfx.ft.repository;

import com.wyfx.ft.model.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-19.14:38
 * description:
 * version:
 */
public interface FileTypeRepo extends BaseRepo<FileType> {
    List<FileType> findByStatus(int yesFileType);

    FileType findById(Long typeId);

    FileType findByUuid(String uuid);

    FileType findByUuidAndStatus(String uuid, int yes);

    Page<FileType> findByStatus(int yes, Pageable page);
}
