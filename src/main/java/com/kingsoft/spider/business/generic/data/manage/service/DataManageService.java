package com.kingsoft.spider.business.generic.data.manage.service;

import com.kingsoft.spider.business.generic.data.manage.dto.DataManageDBDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceByIdDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceDto;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangyujie on 2018/2/1.
 */
public interface DataManageService {
    List<DataManageDBDto> getAllDBList();
    List<List<String>> getData(Long id, Integer index, Integer size);
    Integer getPageSize(Long id, Integer index, Integer size);

    Integer batchesDelete(Long[] ids, Long id) throws SQLException;

    Integer deleteAll(Long id) throws SQLException;

    Integer deleteOne(Long dataId, Long id) throws SQLException;

    Integer fieldContentReplace(FieldContentReplaceDto dto) throws SQLException;

    Integer fieldContentReplaceById(FieldContentReplaceByIdDto dto) throws SQLException;
}
