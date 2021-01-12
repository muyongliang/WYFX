package com.wyfx.upms.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liuxingyu on 2017/2/26 0026.
 */
public class PageUtils {
    /**
     * 设置分页条件
     *
     * @param pageNo pageSize
     * @return
     */
    public static Pageable setPageProperty(int pageNo, int pageSize) {
        Pageable page = new PageRequest(pageNo - 1, pageSize);
        return page;
    }

    /**
     * 分页列表工具类
     */
    public static <T, A> PageQuery pagingList(Class<A> c, Page<T> page, int pageNo, int pageSize) throws Exception {
        List<A> list = new ArrayList<A>(page.getContent().size());
        for (T t : page) {
            A a = c.newInstance();
            System.out.println();
            BeanUtils.copyProperties(t, a);
            list.add(a);
        }
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setFirstPage(page.isFirst());
        int prevPage = pageNo == 1 ? 1 : pageNo - 1;
        pageQuery.setPrevPage(prevPage);
        pageQuery.setLastPage(page.isLast());
        int nextPage = page.hasNext() ? pageNo + 1 : pageNo;
        pageQuery.setNextPage(nextPage);
        pageQuery.setCurrentPageElements(page.getNumberOfElements());
        pageQuery.setTotalPages(page.getTotalPages());
        pageQuery.setTotalElements(page.getTotalElements());
        pageQuery.setContent(list);


        return pageQuery;
    }

    private static <A> String[] getNullPropertyNames(Class<A> source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
