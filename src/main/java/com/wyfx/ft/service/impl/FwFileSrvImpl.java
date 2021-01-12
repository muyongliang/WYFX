package com.wyfx.ft.service.impl;

import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.model.FwFileInfo;
import com.wyfx.ft.model.FwType;
import com.wyfx.ft.model.vo.FwFileInfoVo;
import com.wyfx.ft.repository.FileRepo;
import com.wyfx.ft.repository.FwFileInfoRepo;
import com.wyfx.ft.repository.FwTypeRepo;
import com.wyfx.ft.service.FwFileService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.DateUtils;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/8/18.
 */
@Service
@Transactional
public class FwFileSrvImpl implements FwFileService {
    @Autowired
    private FwFileInfoRepo fwFileInfoRepo;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private FwTypeRepo fwTypeRepo;

    @Override
    public PageQuery getAllFwFile(int pageNo, int pageSize, int flag, String filename, Long fwTypeId, String ip, String startTime, String endTime, String network) throws Exception {
        Sort s = new Sort(Sort.Direction.DESC, "LongTime");
        Pageable page = new PageRequest(pageNo - 1, pageSize, s);
        final FwType fwType;
        if (fwTypeId != null)
            fwType = fwTypeRepo.findOne(fwTypeId);
        else
            fwType = null;
        Page<FwFileInfo> fwFileInfos = fwFileInfoRepo.findAll(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                if (fwType != null)
                    list.add(cb.equal(root.get("swf"), fwType.getType()));

                if (filename != null && !"".equals(filename))
                    list.add(cb.like(root.get("filename"), "%" + filename + "%"));
                if (flag != 1)
                    list.add(cb.equal(root.get("status"), Constants.YES));
                if (ip != null && !"".equals(ip))
                    list.add(cb.like(root.get("ip"), "%" + ip + "%"));
                if (startTime != null && !"".equals(startTime))
                    list.add(cb.between(root.get("longTime"), DateUtils.stringToDate(startTime).getTime(), DateUtils.stringToDate(endTime).getTime()));
                if (network != null && !"".equals(network))
                    list.add(cb.equal(root.get("network"), network));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page);
        List<FwFileInfo> fwFileInfoList = fwFileInfos.getContent();
        for (FwFileInfo fwFileInfo : fwFileInfoList) {
            String remark = fwFileInfo.getRemark();
            Integer status = fwFileInfo.getStatus();
            if (status != null && status == 1) {
                FileInfo fileInfo = fileRepo.findByUuid(fwFileInfo.getUuid());
                if (fileInfo != null)
                    fwFileInfo.setRemark(fileInfo.getRemark());
                fwFileInfoRepo.save(fwFileInfo);
            }
        }
        PageQuery pageQuery = PageUtils.pagingList(FwFileInfoVo.class, fwFileInfos, pageNo, pageSize);

        return pageQuery;
    }

    @Override
    public FwFileInfo getFileByFwFileId(Long fileId) {
        FwFileInfo fwFileInfo = fwFileInfoRepo.findOne(fileId);
        return fwFileInfo;
    }

    @Override
    public List<FwType> getFileType() {
        return fwTypeRepo.findAll();
    }

    @Override
    public void updateRemark(Long id, String remark) {
        FwFileInfo fwFileInfo = fwFileInfoRepo.findOne(id);
        FileInfo fileInfo = fileRepo.findByUuid(fwFileInfo.getUuid());
        if (fileInfo != null) {
            fileInfo.setRemark(remark);
            fileRepo.save(fileInfo);
        }
        fwFileInfo.setRemark(remark);
        fwFileInfoRepo.save(fwFileInfo);
    }

    @Override
    public Map getFwFileCountAndPercent() {
        Map map = new HashMap();
        //文件总数
        long count = fwFileInfoRepo.count();
        //http文件总数
        long httpCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("network"), "http"));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        //smtp文件总数
        long smtpCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("network"), "smtp"));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        //ftp文件总数
        long ftpCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("network"), "ftp"));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        //标记文件总数
        long signCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("status"), Constants.YES));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        //标记http文件总数
        long httpSignCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("network"), "http"));
                list.add(cb.equal(root.get("status"), Constants.YES));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        //标记SMTP文件总数
        long smtpSignCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("network"), "smtp"));
                list.add(cb.equal(root.get("status"), Constants.YES));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        //标记ftp文件总数
        long ftpSignCount = fwFileInfoRepo.count(new Specification<FwFileInfo>() {
            @Override
            public Predicate toPredicate(Root<FwFileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("network"), "ftp"));
                list.add(cb.equal(root.get("status"), Constants.YES));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });

        Map fileMap = new HashMap();
        fileMap.put("count", count);
        fileMap.put("httpCount", httpCount);
        fileMap.put("smtpCount", smtpCount);
        fileMap.put("ftpCount", ftpCount);
        if (count == 0) {
            fileMap.put("ftpPercent", 0);
            fileMap.put("httpPercent", 0);
            fileMap.put("smtpPercent", 0);
        } else {
            Long ftpc = ftpCount * 100 / count;
            Long httpc = httpCount * 100 / count;
            fileMap.put("ftpPercent", ftpc);
            fileMap.put("httpPercent", httpc);
            fileMap.put("smtpPercent", 100 - ftpc - httpc);
        }

        Map fileSignMap = new HashMap();
        fileSignMap.put("signCount", signCount);
        fileSignMap.put("httpSignCount", httpSignCount);
        fileSignMap.put("smtpSignCount", smtpSignCount);
        fileSignMap.put("ftpSignCount", ftpSignCount);
        if (signCount == 0) {
            fileSignMap.put("ftpSignPercent", 0);
            fileSignMap.put("httpSignPercent", 0);
            fileSignMap.put("smtpSignPercent", 0);
        } else {
            Long ftpsc = ftpSignCount * 100 / signCount;
            Long httpsc = httpSignCount * 100 / signCount;
            fileSignMap.put("ftpSignPercent", ftpsc);
            fileSignMap.put("httpSignPercent", httpsc);
            fileSignMap.put("smtpSignPercent", 100 - ftpsc - httpsc);
        }


        map.put("file", fileMap);
        map.put("signFile", fileSignMap);

        return map;
    }

    @Override
    public boolean isExistFile(Long fwFileId) {
        FwFileInfo fwFileInfo = fwFileInfoRepo.findOne(fwFileId);
        String path = fwFileInfo.getPath();
        File file = new File(path);
        boolean b = file.exists();
        return b;
    }
}
