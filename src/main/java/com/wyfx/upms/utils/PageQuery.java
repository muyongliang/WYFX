package com.wyfx.upms.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxingyu on 2017/2/26 0026.
 */
public class PageQuery {
    /**
     * 当前页
     */
    private int pageNo;

    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * 前一页
     */
    private int prevPage;

    /**
     * 是不是第一页
     */
    private boolean isFirstPage;

    /**
     * 后一页
     */
    private int nextPage;

    /**
     * 是不是最后一页
     */
    private boolean isLastPage;

    /**
     * 当前页拥有的元素
     */
    private int currentPageElements;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 总的条数
     */
    private long totalElements;

    /**
     * 在线设备的数量
     */
    private long onlineNum;
    /**
     * 查询的结果数据
     */
    private List content = new ArrayList();

    public long getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(long onlineNum) {
        this.onlineNum = onlineNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getCurrentPageElements() {
        return currentPageElements;
    }

    public void setCurrentPageElements(int currentPageElements) {
        this.currentPageElements = currentPageElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }


    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }
}
