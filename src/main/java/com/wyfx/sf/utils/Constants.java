package com.wyfx.sf.utils;

/**
 * Created by liu on 2017/9/13.
 */
public class Constants {
    /**
     * 操作命令
     */
    public static final int UPLOAD_CMD = 110; //上传命令
    public static final int DELETE_CMD = 111; //删除命令
    public static final int DOWNLOAD_CMD = 101; //下载命令
    public static final int POWER_OFF_CMD = 302; //关机命令
    public static final int IS_REMOTE_CMD = 303; //远程操作命令 1，是 0，否
    public static final int URL_CMD = 30; //URL配置 如：124.25.1.109#www.SF.com
    public static final int NETWORK_CMD = 201; //移动网络 1，开 0，关
    public static final int BASE_STATION_CMD = 202; //基站 1，开 0，关
    public static final int WIFI_CMD = 203; //wifi 1#name#password
    public static final int DORMANT_CMD = 204; //休眠周期时间
    public static final int ONLINE_WATI_CMD = 205; //上线等待时间
    public static final int UPDATE_APK_CMD = 206; //apk更新命令
    public static final int SACN_PATH_CMD = 250; //目录扫描命令

    /**
     * apk更新包加密
     */
    public static final String APK_PATH = "C:/upload/apk"; //解密后apk位置
    public static final String APK_NAME = "sf.apk"; //apk名称

    public static final String CONFIGURATION_NAME = "apk.properties"; //配置文件名称
    public static final String PASSWORD = "e10adc3949ba59abbe56e057f20f883e"; //解密密码 md5

    /**
     * apk更新状态
     */
    public static final int TO_UPDATE_STATUS = -1; //待更新
    public static final int UPDATING_STATUS = 0; //更新中
    public static final int UPDATED_STATUS = 1; //更新完成

    public static final String SERVER_IP = "47.92.0.107";


}
