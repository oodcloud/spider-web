package com.kingsoft.spider.business.generic.data.manage.action;

import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceByIdDto;
import com.kingsoft.spider.business.generic.data.manage.dto.FieldContentReplaceDto;
import com.kingsoft.spider.business.generic.data.manage.service.DataManageService;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Controller
@RequestMapping("/generic/data/manage")
public class DataManageAction extends BaseController{
    @Autowired
    private DataManageService dataManageService;

    @RequestMapping("index.html")
    public String index(Model model){
        model.addAttribute("list",dataManageService.getAllDBList());
        return thymeleaf("index");
    }
    @ResponseBody
    @RequestMapping("getDbDataList.html")
    public CommonResponse queryDbTableData(@RequestParam("id")Long id, @RequestParam("index") Integer index,@RequestParam("size") Integer size){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        commonResponse.put("data",dataManageService.getData(id,index,size));
        commonResponse.put("total",dataManageService.getPageSize(id, index, size));
        return commonResponse;
    }

    @ResponseBody
    @RequestMapping("batchesDelete.html")
    public CommonResponse batchesDelete(@RequestParam("ids[]")  Long[] ids,@RequestParam("id")Long id){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        try {
            Integer count=  dataManageService.batchesDelete(ids,id);
            commonResponse.success("count:"+count);
        } catch (SQLException e) {
            commonResponse.fail("删除失败");
            e.printStackTrace();
        }
        return commonResponse;
    }

    @ResponseBody
    @RequestMapping("deleteAll.html")
    public CommonResponse deleteAll(@RequestParam("id")Long id){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        try {
            Integer count=  dataManageService.deleteAll(id);
            commonResponse.success("count:"+count);
        } catch (SQLException e) {
            commonResponse.fail("删除失败");
            e.printStackTrace();
        }
        return commonResponse;
    }

    @ResponseBody
    @RequestMapping("deleteOne.html")
    public CommonResponse deleteOne(@RequestParam("dataId")  Long dataId,@RequestParam("id")Long id){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        try {
            Integer count=  dataManageService.deleteOne(dataId,id);
            commonResponse.success("count:"+count);
        } catch (SQLException e) {
            commonResponse.fail("删除失败");
            e.printStackTrace();
        }
        return commonResponse;
    }

    @ResponseBody
    @RequestMapping("fieldContentReplace.html")
    public CommonResponse fieldContentReplace(@Valid  FieldContentReplaceDto dto, Errors errors){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        if (errors.hasErrors())
        {
            commonResponse.fail("请检查以下字段",errors.getFieldErrors());
        }else {
            try {
                Integer count=  dataManageService.fieldContentReplace(dto);
                commonResponse.success("count:"+count);
            } catch (SQLException e) {
                commonResponse.fail("替换失败失败");
                e.printStackTrace();
            }

        }
        return commonResponse;
    }

    @ResponseBody
    @RequestMapping("fieldContentReplaceById.html")
    public CommonResponse fieldContentReplaceById(@Valid FieldContentReplaceByIdDto dto,Errors errors){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        if (errors.hasErrors())
        {
            commonResponse.fail("请检查以下字段",errors.getFieldErrors());
        }else {
            try {
                Integer count=  dataManageService.fieldContentReplaceById(dto);
                commonResponse.success("count:"+count);
            } catch (SQLException e) {
                commonResponse.fail("替换失败");
                e.printStackTrace();
            }
        }

        return commonResponse;
    }


}
