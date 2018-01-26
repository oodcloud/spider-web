package com.kingsoft.spider.core.common.shiro;

import com.kingsoft.spider.core.common.support.MD5Utils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wangyujie on 2017/12/28.
 */
public class SaltAwareJdbcRealm extends JdbcRealm {

    private static final Logger log = LoggerFactory.getLogger(SaltAwareJdbcRealm.class);
    private String authenticationQuery = "select pass_word from users where user_name = ?";
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        // Null username is invalid
        if (username == null) {
            throw new AccountException("此领域不允许使用Null用户名.");
        }
        Connection conn = null;
        AuthenticationInfo info = null;
        try {
            conn = dataSource.getConnection();
            String password = getPasswordForUser(conn, username);

            if (password == null) {
                throw new UnknownAccountException("未找到帐号 [" + username + "]");
            }
            SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(username, password, getName());
            saInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
            info = saInfo;

        } catch (SQLException e) {
            final String message = "在验证用户时存在一个SQL错误 [" + username + "]";
            if (log.isErrorEnabled()) {
                log.info(message, e);
            }
            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return info;
    }

    private String getPasswordForUser(Connection conn, String username) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String password = null;
        try {
            ps = conn.prepareStatement(authenticationQuery);
            ps.setString(1, username);
            rs = ps.executeQuery();
            boolean foundResult = false;
            while (rs.next()) {
                if (foundResult) {
                    throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
                }
                password = rs.getString(1);
                foundResult = true;
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }
        return password;
    }

}
