package com.kingsoft.spider.business.common.spider.xmqz.pipeline;

import com.kingsoft.spider.business.common.spider.xmqz.DownloadImg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/27.
 */
public class ImportPipeline implements Pipeline {
    private String folder;
    private final Logger logger= LoggerFactory.getLogger(ImportPipeline.class);

    public ImportPipeline(String folder) {
        this.folder = folder;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        String contents = resultItems.get("contents");
        List<String> imgs = resultItems.get("imgs");
        String time = resultItems.get("time");

        if (title!=null)
        {
            String path=folder+File.separator+time+"#"+title.replaceAll("[\\/:*?\"<>|]","")+File.separator;
            path=path.replaceAll(" ","");
            File file=new File(path);
            if (!file.exists())
            {
                file.mkdirs();
            }
            file=new File(path+File.separator+title.replaceAll("[\\/:*?\"<>|]","")+".txt");
            try {
                file.createNewFile();
                FileWriter writer=new FileWriter(file);
                //文字排版
                contents = contents.replaceAll(" |　","");
                contents = contents.replaceAll("\r\n","\n");
                contents = contents.replaceAll("\n\n","\n");
                contents = contents.replaceAll("\n\n","\n");
                contents = contents.replaceAll("\n\n","\n");
                contents = contents.replaceAll("\n\n","\n");
                contents = contents.replaceAll("\n","  \n");
                contents = contents.replace("\\n\\n","\n");

                writer.write(contents);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file=new File(path+File.separator+"images");
            if (!file.exists())
            {
                file.mkdirs();
            }
            for (int i = 0; i <imgs.size() ; i++) {
                try {
                    logger.info(" ImportPipeline DownLoad Image doing:"+imgs.get(i));
                    DownloadImg.save(file.getPath(),imgs.get(i));
                    logger.info(" ImportPipeline DownLoad Image finished");
                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
