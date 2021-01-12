package com.wyfx.cs.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by liu on 2017/12/13.
 */
public interface IssuesService {
    void saveIssues(String projectKey);

    Map serachIssues(Long scanListId) throws Exception;

    Map serachIssuesAndCode(Long scanListId) throws Exception;

    Map getCodeByIssuesId(Long issuesId) throws UnsupportedEncodingException;
}
