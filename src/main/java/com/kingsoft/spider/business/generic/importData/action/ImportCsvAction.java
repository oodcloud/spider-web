package com.kingsoft.spider.business.generic.importData.action;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.generic.importData.dto.ExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ImportParamRequest;
import com.kingsoft.spider.business.generic.importData.service.ImportCsvService;
import com.kingsoft.spider.core.common.excel.ExcelException;
import com.kingsoft.spider.core.common.excel.ExcelUtil;
import com.kingsoft.spider.core.common.support.BaseController;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.WriterOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/26.
 */
@Controller
@RequestMapping("/generic/import")
public class ImportCsvAction extends BaseController {
    @Autowired
    private ImportCsvService importCsvService;

    @RequestMapping("index.html")
    public String index() {
        return thymeleaf("index");
    }

    @ResponseBody
    @RequestMapping("import.html")
    public void importExcel(ImportParamRequest request, HttpServletResponse response) {
        List<ExcelEntity> excelEntities = importCsvService.selectByTime(request);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("dateTime", "评论时间");
        map.put("text", "评论内容");
        map.put("title", "话题");
        map.put("author", "评论者");
        String fileName = request.getStart() + "--" + request.getEnd() + "[";
        switch (request.getType()) {
            case 1:
                fileName = fileName + "自由禁区微博]";
                break;
            case 2:
                fileName = fileName + "小米枪战微博]";
                break;
            case 3:
                fileName = fileName + "反恐行动微博]";
                break;
            case 4:
                fileName = fileName + "自由禁区论坛]";
                break;
            case 5:
                fileName = fileName + "自由禁区B站]";
                break;
            case 6:
                fileName = fileName + "小米枪战B站]";
                break;
            case 7:
                fileName = fileName + "反恐行动B站]";
                break;
            case 8:
                fileName = fileName + "自由禁区贴吧]";
                break;
            case 9:
                fileName = fileName + "小米枪战贴吧]";
                break;
            case 10:
                fileName = fileName + "反恐行动贴吧]";
                break;
            case 11:
                fileName = fileName + "制作人微博]";
                break;
            case 12:
                fileName = fileName + "自由禁区微信]";
                break;
            case 13:
                fileName = fileName + "小米枪战微信]";
                break;
            case 14:
                fileName = fileName + "反恐行动微信]";
                break;

        }
        try {
            if (excelEntities!=null&&excelEntities.size()!=0)
            {
                ExcelUtil.listToExcel(fileName, excelEntities, map, "评论数据", response);
            }else {
                excelEntities=new ArrayList<>();
                ExcelEntity entity=new ExcelEntity();
                entity.setText("查询当前日期无数据");
                excelEntities.add(entity);
                ExcelUtil.listToExcel(fileName, excelEntities, map, "评论数据", response);
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
