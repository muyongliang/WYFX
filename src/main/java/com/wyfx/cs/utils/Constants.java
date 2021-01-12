package com.wyfx.cs.utils;

/**
 * Created by liu on 2017/12/7.
 */
public class Constants {
    /**
     * 扫描状态
     */
    public final static int TO_SCAN_STATUS = 0;//待扫描
    public final static int SCANING = 1;       //扫描中
    public final static int SCANED = 2;        //已扫描

    public final static String TOKEN = "4b6d51e73edb4f0cafd17a4510bca7d4";
    public final static String ENCRYPT_TOKEN = "4b6d51e73edb4f0cafd17a4510bca7d4";

    //sonar-scanner.bat -Dsonar.projectKey=aa -Dsonar.sources=. -Dsonar.host.url=http://localhost:9000 -Dsonar.login=77c49d74a71e01bdd8964b31134b70049dc81032
    //get VULNERABILITY  CODE_SMELL
    public final static String API_ISSUES_SEARCH = "http://localhost:9000/api/issues/search?componentKeys=test22&s=FILE_LINE&resolved=false&types=CODE_SMELL%2CVULNERABILITY&ps=600&facets=severities%2Ctypes&additionalFields=_all";
    //http://localhost:9000/api/sources/lines?key=test22%3AAdmin%2FInclude%2Ffunction.php&from=501&to=1001
    public final static String API_SOURCES_LINES = "http://localhost:9000/api/sources/lines";
}
