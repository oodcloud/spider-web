package com.kingsoft.spider.core.home.login.action;

import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import com.kingsoft.spider.core.common.support.MD5Utils;
import com.kingsoft.spider.core.home.login.dao.UserRequest;
import com.kingsoft.spider.core.home.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private  LoginService loginService;

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
        try {
            SecurityUtils.getSubject().login(token);
            loginService.updateGenerateTime(userRequest.getUsername());
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

    @RequestMapping("changePassword.html")
    @ResponseBody
   public CommonResponse changePassword(@RequestParam("pwd") String pwd){
       CommonResponse commonResponse=CommonResponse.createCommonResponse();
        Session session=SecurityUtils.getSubject().getSession();
       String userName= (String) session.getAttribute("user");
       // source为密码 salt为用户名
       String newPassword =  new Sha256Hash(pwd,userName).toBase64();
       System.out.println(newPassword);
       Integer count=  loginService.updatePassword(userName,newPassword);
       if (count==1)
       {
           SecurityUtils.getSubject().logout();
           commonResponse.success();
       }else {
           commonResponse.fail("修改密码失败");
       }
       return commonResponse;
   }

    public static void main(String[] args) {
        String newPassword =  new Sha256Hash("abc123456","admin").toBase64();
        System.out.println(newPassword);
    }

}
