package com.kingsoft.spider.business.datadb;

import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;

import java.sql.Connection;
import java.util.List;

/**
 * Created by wangyujie on 2018/3/20.
 */
public class DbUseHandleUtils {

    public static Integer getColumnCount(SpiderConfigInfoDto spiderConfigInfoDto) {
        DataManageDbInfoDto dataManageDbInfoDto=new DataManageDbInfoDto();
        dataManageDbInfoDto.setDbAddress(spiderConfigInfoDto.getDbAddress());
        dataManageDbInfoDto.setDbName(spiderConfigInfoDto.getDbName());
        dataManageDbInfoDto.setDbPassword(spiderConfigInfoDto.getDbPassWord());
        dataManageDbInfoDto.setDbUsername(spiderConfigInfoDto.getDbUserName());
        dataManageDbInfoDto.setDbTable(spiderConfigInfoDto.getDbTable());
        DbUseHandle dbUse=new DbUseHandle(dataManageDbInfoDto);
        return dbUse.getColumnCount();
    }

    public static List<String> getColumnCount(Connection connection, String table)
    {
        DbUseHandle dbUse=new DbUseHandle(connection,table);
        return dbUse.getColumnCountByConnection();
    }

}
