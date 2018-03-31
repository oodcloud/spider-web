package com.kingsoft.spider.business.generic.data.manage.service.impl;

import com.kingsoft.spider.business.datadb.DbUseHandle;
import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import com.kingsoft.spider.business.generic.data.manage.dto.DataManageDBDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceByIdDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceDto;
import com.kingsoft.spider.business.generic.data.manage.mapper.DataManageMapper;
import com.kingsoft.spider.business.generic.data.manage.service.DataManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Service
public class DataManageServiceImpl implements DataManageService {
    @Autowired
    private DataManageMapper dataManageMapper;

    @Override
    public List<DataManageDBDto> getAllDBList() {
        return dataManageMapper.queryAllDBList();
    }

    @Override
    public  List<List<String>> getData(Long id, Integer index, Integer size) {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(id);
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);
        return dbUseHandle.getData(index,size);
    }

    @Override
    public Integer getPageSize(Long id, Integer index, Integer size) {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(id);
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);
        return dbUseHandle.pageSize();
    }

    @Override
    public Integer batchesDelete(Long[] ids, Long id) throws SQLException {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(id);
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);
        return dbUseHandle.batchesDelete(ids);
    }

    @Override
    public Integer deleteAll(Long id) throws SQLException {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(id);
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);
        return dbUseHandle.emptyTable();
    }

    @Override
    public Integer deleteOne(Long dataId, Long id) throws SQLException {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(id);
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);
        Long[] ids =new Long[1];
        ids[0]=dataId;
        return dbUseHandle.batchesDelete(ids);
    }

    @Override
    public Integer fieldContentReplace(FieldContentReplaceDto fieldContentReplaceDto) throws SQLException {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(fieldContentReplaceDto.getId());
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);

        return dbUseHandle.replaceContent(fieldContentReplaceDto);
    }

    @Override
    public Integer fieldContentReplaceById(FieldContentReplaceByIdDto fieldContentReplaceByIdDto) throws SQLException {
        DataManageDbInfoDto dbInfoDto= dataManageMapper.queryDbInfoById(fieldContentReplaceByIdDto.getId());
        DbUseHandle dbUseHandle=new DbUseHandle(dbInfoDto);
        return dbUseHandle.replaceContent(fieldContentReplaceByIdDto);
    }


}
