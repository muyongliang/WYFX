package com.wyfx.sf.repository;

import com.wyfx.sf.model.Command;

/**
 * Created by liu on 2017/9/13.
 */
public interface CommandRepo extends BaseRepo<Command> {
    Command findByDeviceIdAndCmd(Long deviceId, int uploadCmd);
}
