package com.wyfx.sf.service.impl;

import com.wyfx.sf.model.Command;
import com.wyfx.sf.model.Configuration;
import com.wyfx.sf.model.ParameterLog;
import com.wyfx.sf.model.SdFile;
import com.wyfx.sf.repository.CommandRepo;
import com.wyfx.sf.repository.ConfigurationRepo;
import com.wyfx.sf.repository.ParameterLogRepo;
import com.wyfx.sf.repository.SdFileRepo;
import com.wyfx.sf.service.SdFileService;
import com.wyfx.sf.utils.Constants;
import com.wyfx.sf.utils.DateUtils;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2017/9/13.
 */
@Service
@Transactional
public class SdFileSrvImpl implements SdFileService {
    @Autowired
    private SdFileRepo sdFileRepo;
    @Autowired
    private CommandRepo commandRepo;
    @Autowired
    private ParameterLogRepo parameterLogRepo;
    @Autowired
    private ConfigurationRepo configurationRepo;

    @Override
    public List<SdFile> getFileAndFolder(String path, Long deviceId) {


        //通过deviceId,path,查询文件目录
        List<SdFile> sdFiles = sdFileRepo.findByDeviceIdAndPath(deviceId, path);
        if (sdFiles == null || sdFiles.size() == 0)
            return null;
        return sdFiles;
    }

    @Override
    public boolean saveUploadFileCommand(Long deviceId, String args) {
        saveCommand(deviceId, args, Constants.UPLOAD_CMD);
        return true;
    }

    /**
     * 保存命令
     *
     * @param deviceId 设备ID
     * @param args     参数
     * @param cmd      命令类型
     */
    public void saveCommand(Long deviceId, String args, int cmd) {
        Command command = commandRepo.findByDeviceIdAndCmd(deviceId, cmd);
        if (command == null) {
            //没有上传命令，新添加
            command = new Command();
            command.setArgs(args);
            command.setCmd(cmd);
            command.setDeviceId(deviceId);
        } else {
            String commandArgs = command.getArgs();
            //如果args不为空，在参数后面追加参数，以#号分隔
            if (commandArgs != null && !"".equals(commandArgs)) {
                command.setArgs(commandArgs + "#" + args);
            } else {
                command.setArgs(args);
            }
        }
        commandRepo.save(command);
    }

    @Override
    public void saveDownloadFileCommand(Long deviceId, String path) {
        saveCommand(deviceId, path, Constants.DOWNLOAD_CMD);
    }

    @Override
    public void saveDeleteFileCommand(Long deviceId, String path) {
        saveCommand(deviceId, path, Constants.DELETE_CMD);
    }

    @Override
    public PageQuery getParameterLog(Long deviceId, Long startTime, Long endTime, int pageNo, int pageSize) throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(pageNo - 1, pageSize, sort);

        List cmdList = new ArrayList();
        cmdList.add(Constants.DELETE_CMD);
        cmdList.add(Constants.DOWNLOAD_CMD);
        cmdList.add(Constants.UPLOAD_CMD);
        //查询删除，下载，上传的日志
        Page<ParameterLog> parameterLogList;
        if (startTime == null || endTime == null) {
            parameterLogList = parameterLogRepo.findByDeviceIdAndDowncommandIn(deviceId, cmdList, page);
        } else {
            parameterLogList = parameterLogRepo.findByDeviceIdAndDowncommandInAndPtimeBetween(deviceId, cmdList, startTime, endTime, page);
        }

        List<ParameterLog> parameterLogs = parameterLogList.getContent();
        for (ParameterLog parameterLog : parameterLogs) {
            if (parameterLog.getFilename() == null || parameterLog.getPath() == null) {
                String args = parameterLog.getArgs();
                if (parameterLog.getDowncommand() == Constants.UPLOAD_CMD) {
                    String parameterLogArgs = parameterLog.getArgs();
                    int indexOf = parameterLogArgs.indexOf(":");
                    args = parameterLogArgs.substring(indexOf + 1);
                }

                int lastIndexOf = args.lastIndexOf("/");
                parameterLog.setPath(args.substring(0, lastIndexOf));
                parameterLog.setFilename(args.substring(lastIndexOf + 1));

            }
            if (parameterLog.getpDate() == null) {
                //时间格式化
                Long ptime = parameterLog.getPtime();
                if (ptime != null)
                    parameterLog.setpDate(DateUtils.stampToDate(ptime.toString()));
            }
            if (parameterLog.geteDate() == null) {
                //时间格式化
                Long etime = parameterLog.getEtime();
                if (etime != null)
                    parameterLog.seteDate(DateUtils.stampToDate(etime.toString()));
            }
            parameterLogRepo.save(parameterLog);

        }
        PageQuery pageQuery = PageUtils.pagingList(ParameterLog.class, parameterLogList, pageNo, pageSize);
        return pageQuery;
    }

    @Override
    public void powerOff(Long deviceId) {
        Command command = commandRepo.findByDeviceIdAndCmd(deviceId, Constants.POWER_OFF_CMD);
        if (command == null) {
            command = new Command();
            command.setDeviceId(deviceId);
            command.setCmd(Constants.POWER_OFF_CMD);
        }
        command.setArgs("power off");
        commandRepo.save(command);
    }

    @Override
    public void saveIsRemoteCommand(Long deviceId, int status) {

        Configuration configuration = configurationRepo.findByDeviceId(deviceId);
        if (configuration == null) {
            configuration = new Configuration();
            configuration.setDeviceId(deviceId);
        }
        configuration.setRemoteStatus(status);
        configurationRepo.save(configuration);
        Command command = commandRepo.findByDeviceIdAndCmd(deviceId, Constants.ONLINE_WATI_CMD);
        if (command == null) {
            command = new Command();
            command.setDeviceId(deviceId);
            command.setCmd(Constants.ONLINE_WATI_CMD);
        }
        //远程操作
        if (status == 1) {
            command.setArgs(String.valueOf(0));
        } else {
            command.setArgs(String.valueOf(configuration.getOnlineWaitTime()));
        }
        commandRepo.save(command);


    }

    @Override
    public Object getFile(Long deviceId, String path, String filename) {
        SdFile sdFile = sdFileRepo.findByDeviceIdAndPathAndFilename(deviceId, path, filename);
        if (sdFile == null)
            return null;
        return sdFile.getFile();
    }

    @Override
    public SdFile findByDeviceIdFirst(Long deviceId) {
        Pageable page = new PageRequest(0, 1);
        List<SdFile> sdFiles = sdFileRepo.findByDeviceId(deviceId, page);
        if (sdFiles != null && sdFiles.size() > 0)
            return sdFiles.get(0);

        return null;
    }

    @Override
    public void saveFindFileCommand(Long deviceId, String path) {
        saveCommand(deviceId, path, Constants.DOWNLOAD_CMD);


    }
}
