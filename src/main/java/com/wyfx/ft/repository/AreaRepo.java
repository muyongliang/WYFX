package com.wyfx.ft.repository;

import com.wyfx.ft.model.Area;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AreaRepo extends BaseRepo<Area> {

    List<Area> findByMid(int num, Pageable page);

    Area findByMid(Long num);
}
