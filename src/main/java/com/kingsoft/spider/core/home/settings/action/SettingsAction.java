package com.kingsoft.spider.core.home.settings.action;

import com.github.pagehelper.PageInfo;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import com.kingsoft.spider.core.home.settings.dto.UserDto;
import com.kingsoft.spider.core.home.settings.dto.UserInfoDto;
import com.kingsoft.spider.core.home.settings.service.SettingService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Security;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/29.
 */
@Controller
@RequestMapping("/")
public class SettingsAction extends BaseController {

    @Autowired
    private SettingService settingService;

    @RequestMapping("settings.html")
    public String index(Model model) {
        String userName = (String) SecurityUtils.getSubject().getSession().getAttribute("user");
        Integer role = settingService.getRoleByUserName(userName);
        model.addAttribute("role", role);
        return thymeleaf("settings");
    }

    @RequestMapping("druid.html")
    public String druid() {
        return "redirect:/druid/index.html";
    }


    @RequestMapping("getUserInfos.html")
    @ResponseBody
    public CommonResponse getTableData(Integer pageSize, Integer pageIndex) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        PageInfo info = settingService.getUserInfoList(pageIndex, pageSize);
        commonResponse.put("data", info.getList());
        commonResponse.put("total", info.getTotal());
        return commonResponse;
    }

    @RequestMapping("getUser.html")
    @ResponseBody
    public CommonResponse getUser(Long id) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        commonResponse.put("data", settingService.getUserById(id));

        return commonResponse;
    }


    @RequestMapping("saveUser.html")
    @ResponseBody
    public CommonResponse saveUser(@Valid UserDto dto, Errors errors) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        if (errors.hasErrors()) {
            commonResponse.fail("请检查一下字段", errors.getFieldErrors());
        } else {
            try {
                String password = dto.getPassWord();
                String encryptPwd = new Sha256Hash(password, dto.getUserName()).toBase64();
                dto.setPassWord(encryptPwd);
                settingService.saveUser(dto);
                commonResponse.success();
            } catch (Exception e) {
                commonResponse.fail("该用户已经存在");
            }
        }
        return commonResponse;
    }

    @RequestMapping("updateUser.html")
    @ResponseBody
    public CommonResponse updateUser(@Valid UserDto dto, Errors errors) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        if (errors.hasErrors()) {
            commonResponse.fail("请检查一下字段", errors.getFieldErrors());
        } else {
            String password = dto.getPassWord();
            String encryptPwd = new Sha256Hash(password, dto.getUserName()).toBase64();
            dto.setPassWord(encryptPwd);
            settingService.updateUser(dto);
            commonResponse.success();
        }
        return commonResponse;
    }

    @RequestMapping("delUser.html")
    @ResponseBody
    public CommonResponse delUser(Long id) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        settingService.delUser(id);
        commonResponse.success();
        return commonResponse;
    }

}
