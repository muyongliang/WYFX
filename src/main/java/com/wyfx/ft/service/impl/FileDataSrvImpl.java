package com.wyfx.ft.service.impl;

import com.wyfx.ft.model.DataInfo;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.model.vo.DataInfoVo;
import com.wyfx.ft.repository.FileDataRepo;
import com.wyfx.ft.repository.FileDataRepoImpl;
import com.wyfx.ft.repository.FileRepo;
import com.wyfx.ft.service.FileDataService;
import com.wyfx.ft.utils.ListUtils;
import com.wyfx.upms.utils.DateUtils;
import com.wyfx.upms.utils.ModelChange;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * Created by liu on 2017/8/9.
 */
@Service
@Transactional
public class FileDataSrvImpl implements FileDataService {
    @Autowired
    private FileDataRepo fileDataRepo;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private FileDataRepoImpl fileDataRepoImpl;

    @Override
    public PageQuery getFileDataByFileId(Long fileId, int pageNo, int pageSize) throws Exception {
        //查询文件
        FileInfo fileInfo = fileRepo.findById(fileId);
        //分页
        Sort s = new Sort(Sort.Direction.DESC, "Time");
        Pageable page = new PageRequest(pageNo - 1, pageSize, s);
        Page<DataInfo> dataInfos = fileDataRepo.findByFileInfo(fileInfo, page);
        PageQuery pageQuery = PageUtils.pagingList(DataInfoVo.class, dataInfos, pageNo, pageSize);
        return pageQuery;
    }

    @Override
    public List getCityOrRegionAndCount(Long fileId, int flag, int pageSize, String startTime, String endTime) {
        return fileDataRepoImpl.getCityOrRegionAndCount(fileId, flag, pageSize, startTime, endTime);
    }

    @Override
    public Map getCityAndPercent(Long fileId, String startTime, String endTime, String type) {
        return fileDataRepoImpl.getCityAndPercent(fileId, startTime, endTime, type);
    }

    @Override
    public Map getCountByDay(Long fileId, String startTime, String endTime) {
        return fileDataRepoImpl.getCountByDay(fileId, startTime, endTime);
    }

    @Override
    public List getDay(String startTime, String endTime) {
        if (startTime == null || "".equals(startTime) || endTime == null || "".equals(endTime)) {
            startTime = "2017-07-01";
            endTime = DateUtils.dateToString(new Date());
        }
        List<String> timeList = DateUtils.collectLocalDates(startTime, endTime);
        return timeList;
    }

    @Override
    public PageQuery getSameIp(Long firstId, Long secondId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception {
        Set<DataInfoVo> set = new HashSet();
        List<DataInfo> firstDataInfos = fileDataRepo.findAll(new Specification<DataInfo>() {
            @Override
            public Predicate toPredicate(Root<DataInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                FileInfo fileRepoOne = fileRepo.findOne(firstId);
                list.add(cb.equal(root.get("fileInfo"), fileRepoOne));
                if (startTime != null) {
                    list.add(cb.between(root.get("time"), startTime, endTime));
                }
                //list.add(query.groupBy(root.get("ip")).getRestriction());
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        List<DataInfo> secondDataInfos = fileDataRepo.findAll(new Specification<DataInfo>() {
            @Override
            public Predicate toPredicate(Root<DataInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                if (startTime != null) {
                    list.add(cb.between(root.get("time"), startTime, endTime));
                }
                FileInfo fileRepoOne = fileRepo.findOne(secondId);
                list.add(cb.equal(root.get("fileInfo"), fileRepoOne));

                //  list.add(query.groupBy(root.get("ip")).getRestriction());
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        for (DataInfo firstDataInfo : firstDataInfos) {
            String firstDataInfoIp = firstDataInfo.getIp();
            for (DataInfo secondDataInfo : secondDataInfos) {
                String secondDataInfoIp = secondDataInfo.getIp();
                if (firstDataInfoIp.equals(secondDataInfoIp)) {
                    set.add(ModelChange.changeEntity(DataInfoVo.class, firstDataInfo));
                    set.add(ModelChange.changeEntity(DataInfoVo.class, secondDataInfo));
                    break;
                }
            }

        }

        List<DataInfoVo> list = new ArrayList();
        list.addAll(set);
        //排序
        Comparator mycmp = ComparableComparator.getInstance();
        mycmp = ComparatorUtils.naturalComparator();
        Comparator cmp = new BeanComparator("time", mycmp);
        Collections.sort(list, cmp);

        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setTotalElements(list.size());
        if (list.size() > 0) {
            List<List<DataInfoVo>> lists = ListUtils.splitList(list, pageSize);
            pageQuery.setContent(lists.get(pageNo - 1));
        }

        return pageQuery;
    }


}
