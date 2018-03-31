package com.kingsoft.spider.business.datadb;

import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import com.kingsoft.spider.business.generic.home.dto.ShowDataFieldDto;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wangyujie on 2018/3/22.
 */
public class ShowDataDbUseHandle extends DbUse{

    public ShowDataDbUseHandle(DataManageDbInfoDto dto) {
        super.setDataSource(dto);
    }

    public Integer getTotalCount(ShowDataFieldDto queryFields)
    {
        StringBuilder sql= new StringBuilder("SELECT COUNT(*) AS totalCount FROM " + queryFields.getDbTable());
        List<SpiderConfigInfoDto.MatchField> fields=queryFields.getFieldList();
        if (fields.size()!=0)
        {
             sql.append(" WHERE ");

             for (SpiderConfigInfoDto.MatchField field:fields)
            {
                sql.append(" ").append(field.getFieldEnglishName()).append(" = '").append(field.getDefaultValue()).append("' AND");
            }
            sql.delete(sql.length()-3,sql.length());
        }
        try {
            return super.runner.query(getConnection(),sql.toString(),new FeildRowResultSetHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Integer nowTotalCount(ShowDataFieldDto queryFields){
        StringBuilder sql= new StringBuilder("SELECT COUNT(*) AS totalCount FROM " + queryFields.getDbTable());
        List<SpiderConfigInfoDto.MatchField> fields=queryFields.getFieldList();


        if (fields.size()!=0)
        {
            sql.append(" WHERE ");

            for (SpiderConfigInfoDto.MatchField field:fields)
            {
                sql.append(" ").append(field.getFieldEnglishName()).append(" = '").append(field.getDefaultValue()).append("' AND");
            }
            LocalDateTime localDateTime=LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            Timestamp start=Timestamp.valueOf(localDateTime);
            Timestamp end=new Timestamp(System.currentTimeMillis());
            sql.append(" creat_time  BETWEEN '").append(start).append("' AND '").append(end).append("'");
        }

        try {
            return super.runner.query(getConnection(),sql.toString(),new FeildRowResultSetHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

}
