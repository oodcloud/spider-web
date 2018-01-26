package com.kingsoft.spider.business.generic.importData.dto.Event;

/**
 * Created by wangyujie on 2017/12/28.
 */
public class ExcelFactory {
    public static AbstractExcelList create(int type){
        AbstractExcelList excelList=null;
        switch (type)
        {
            case 1://自由禁区微博
                excelList=new WeiboExtopiaExcelList();
              break;
            case 2://小米枪战微博
                excelList=new WeiboXmqzExcelList();
                break;
            case 3://反恐行动微博
                excelList=new WeiboMatExcelList();
                break;
            case 4://自由禁区论坛
                excelList=new BBSExcelList();
                break;
            case 5://自由禁区B站
                excelList=new BExtopiaExcelList();
                break;
            case 6://小米枪战B站
                excelList=new BXmqzExcelList();
                break;
            case 7://反恐行动B站
                excelList=new BMatExcelList();
                break;
            case 8://自由禁区贴吧
                excelList=new TiebaExtopiaExcelList();
                break;
            case 9://小米枪战贴吧
                excelList=new TiebaXmqzExcelList();
                break;
            case 10://反恐行动贴吧
                excelList=new TiebaMatExcelList();
                break;
            case 11://制作人微博
                excelList=new WeiboProducerExcelList();
                break;
            case 12://自由禁区微信
                excelList=new WechatExtopiaExcelList();
                break;
            case 13://小米枪战微信
                excelList=new WeiboXmqzExcelList();
                break;
            case 14://反恐行动微信
                excelList=new WeiboMatExcelList();
                break;

        }
        return excelList;
    }
}
