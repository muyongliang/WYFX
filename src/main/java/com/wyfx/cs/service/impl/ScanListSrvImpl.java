package com.wyfx.cs.service.impl;

import com.wyfx.cs.model.ScanList;
import com.wyfx.cs.model.vo.ScanListVo;
import com.wyfx.cs.repository.ScanListRepo;
import com.wyfx.cs.service.ScanListService;
import com.wyfx.cs.utils.Constants;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2017/12/7.
 */
@Service
@Transactional
public class ScanListSrvImpl implements ScanListService {
    @Autowired
    private ScanListRepo scanListRepo;

    @Override
    public PageQuery getScanListByStatus(Integer status, int pageNo, int pageSize, Date startTime, Date endTime) throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "startTime");
        Pageable page = new PageRequest(pageNo - 1, pageSize, sort);
        Page<ScanList> scanLists = scanListRepo.findAll(new Specification<ScanList>() {
            @Override
            public Predicate toPredicate(Root<ScanList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                if (status == Constants.SCANED) {
                    list.add(cb.equal(root.get("status"), status));
                } else {
                    list.add(cb.notEqual(root.get("status"), Constants.SCANED));
                }

                if (startTime != null) {
                    list.add(cb.between(root.get("longTime"), startTime.getTime(), endTime.getTime()));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page);

        PageQuery pageQuery = PageUtils.pagingList(ScanListVo.class, scanLists, pageNo, pageSize);
        List<ScanListVo> scanListVoList = pageQuery.getContent();
        List<ScanList> scanListList = scanLists.getContent();
        for (int i = 0; i < scanListList.size(); i++) {
            ScanList scanList = scanListList.get(i);
            ScanListVo scanListVo = scanListVoList.get(i);
            scanListVo.setItemName(scanList.getItem().getItemName());
            scanListVo.setTeamName(scanList.getTeam().getTeamName());
        }

        return pageQuery;
    }


}
