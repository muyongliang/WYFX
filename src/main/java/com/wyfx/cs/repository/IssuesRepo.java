package com.wyfx.cs.repository;

import com.wyfx.cs.model.Issues;
import com.wyfx.cs.model.ScanList;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by liu on 2017/12/13.
 */
public interface IssuesRepo extends BaseRepo<Issues> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<Issues> findByScanListAndSeverity(ScanList scanList, String s);
}
