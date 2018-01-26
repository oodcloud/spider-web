package com.kingsoft.spider.core.home.login.action;

import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import com.kingsoft.spider.core.common.support.MD5Utils;
import com.kingsoft.spider.core.home.login.dao.UserRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyujie on 2017/12/20.
 */
@RequestMapping("/")
@Controller
public class LoginAction extends BaseController {
    private static transient final Logger log = LoggerFactory.getLogger(LoginAction.class);

    @RequestMapping("login.html")
    public String login() {
        return thymeleaf("login");
    }

    @ResponseBody
    @RequestMapping("user-check.html")
    public CommonResponse checkLogin(@Validated UserRequest userRequest, BindingResult bindingResult) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        Map<String, String> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            commonResponse.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userRequest.getUsername(), userRequest.getPassword());
//        token.setRememberMe(true);
        log.info("Authenticat Token:" + token.toString());
        try {
            SecurityUtils.getSubject().login(token);
            SecurityUtils.getSubject().getSession().setAttribute("user",userRequest.getUsername());
            map.put("statusCode", "200");
            map.put("statusContent", "匹配成功");
        } catch (UnknownAccountException uae) {
            map.put("statusCode", "450");
            map.put("statusContent", "未知账号");
        } catch (IncorrectCredentialsException ice) {
            map.put("statusCode", "450");
            map.put("statusContent", "验证凭证错误");
        } catch (LockedAccountException lae) {
            map.put("statusCode", "450");
            map.put("statusContent", "锁定的帐号异常");
        } catch (ExcessiveAttemptsException eae) {
            map.put("statusCode", "450");
            map.put("statusContent", "过度的尝试例外");
        }  catch (AuthenticationException e) {
            log.debug("Error authenticating.", e);
            map.put("statusCode", "450");
            map.put("statusContent", "用户名或密码不正确");
        }
        commonResponse.setData(map);
        return commonResponse;
    }

   private void changePassword(){
//        source为密码 salt为用户名
       String password =  new Sha256Hash("admin", "admin").toBase64();
   }


}
