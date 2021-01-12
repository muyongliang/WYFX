package com.wyfx.sf.repository;

import com.wyfx.sf.model.NodeInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by liu on 2017/9/18.
 */
public interface NodeInfoRepo extends BaseRepo<NodeInfo> {
    List<NodeInfo> findByDeviceId(Long deviceId, Pageable page);
}
