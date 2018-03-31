package com.kingsoft.spider.business.datadb;

import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceByIdDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceDto;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangyujie on 2018/3/15.
 */
public class DbUseHandle extends DbUse {
    private DataManageDbInfoDto dto;
    private Connection connection;
    private String table;


    public DbUseHandle(DataManageDbInfoDto dto) {
        this.dto = dto;
        super.setDataSource(dto);
    }

    public DbUseHandle(Connection connection, String table) {
        this.connection = connection;
        this.table = table;
    }

    public  List<List<String>> getData(Integer index, Integer size) {
        try {
            String sql="SELECT * FROM "+dto.getDbTable()+" LIMIT "+index+" , "+size;
            return super.runner.query(getConnection(),sql,new CommonResultSetHandler());
        } catch (SQLException e) {
           throw new RuntimeException(e);
        } finally {
            close();
        }
    }
    public Integer pageSize()
    {
        try {
            String sql="SELECT count(*) AS totalCount FROM "+dto.getDbTable();
            return super.runner.query(getConnection(), sql, resultSet -> {
                resultSet.next();
                return resultSet.getInt("totalCount");
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    public Integer batchesDelete(Long[] ids) throws SQLException {
        if (ids.length==0)
        {
            return 0;
        }
        try {
            StringBuilder sql = new StringBuilder("DELETE FROM  " + dto.getDbTable() + " WHERE id in (");
            for (int i = 0; i < ids.length; i++) {
                if (i != ids.length - 1) {
                    sql.append(ids[i]).append(",");
                } else {
                    sql.append(ids[i]).append(")");
                }
            }
            return super.runner.update(getConnection(),sql.toString());

        }  finally {
                close();
        }
    }

    public Integer emptyTable() throws SQLException {
        String sql="DELETE FROM  " + dto.getDbTable();
        try {
            return super.runner.update(getConnection(),sql);
        }finally {
            close();
        }

    }

    public Integer replaceContent(FieldContentReplaceDto fieldContentReplaceDto) throws SQLException {
        String sql="update "+dto.getDbTable()+" set "+fieldContentReplaceDto.getField()
                +" =replace("+fieldContentReplaceDto.getField()+",'"+fieldContentReplaceDto.getOrginContent()
                +"','"+fieldContentReplaceDto.getTargetContent()+
                "') ";
        if (fieldContentReplaceDto.getStartTime()!=null&&fieldContentReplaceDto.getEndTime()!=null)
        {
            Timestamp timestampStart=new Timestamp(fieldContentReplaceDto.getStartTime().getTime());
            Timestamp timestampEnd=new Timestamp(fieldContentReplaceDto.getEndTime().getTime());
            sql+= "WHERE creat_time BETWEEN '"+timestampStart+"' AND '"+timestampEnd+"'";
        }
        try {
            return super.runner.update(getConnection(),sql);
        }finally {
            close();
        }
    }

    public Integer replaceContent(FieldContentReplaceByIdDto fieldContentReplaceByIdDto) throws SQLException {
        StringBuilder sql= new StringBuilder("update " + dto.getDbTable() + " set " + fieldContentReplaceByIdDto.getField()
                + " =replace(" + fieldContentReplaceByIdDto.getField() + ",'" + fieldContentReplaceByIdDto.getOrginContent()
                + "','" + fieldContentReplaceByIdDto.getTargetContent() +
                "') WHERE id in (");
        Long[] ids=fieldContentReplaceByIdDto.getIds();
        for (int i = 0; i < ids.length; i++) {
            if (i != ids.length - 1) {
                sql.append(ids[i]).append(",");
            } else {
                sql.append(ids[i]).append(")");
            }
        }
        try {
            return super.runner.update(getConnection(), sql.toString());
        }finally {
            close();
        }
    }
    public Integer getColumnCount(){
        String sql="SELECT * FROM "+dto.getDbTable();
        Integer count=0;
        try {
            count= super.runner.query(getConnection(), sql,new FeildColumnResultSetHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return count;
    }

    public  List<String> getColumnCountByConnection(){

        String sql="SELECT * FROM "+this.table;
        List<String> colNames=null;
        try {
                colNames= super.runner.query(connection, sql,new FeildsResultSetHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return colNames;
    }

}
