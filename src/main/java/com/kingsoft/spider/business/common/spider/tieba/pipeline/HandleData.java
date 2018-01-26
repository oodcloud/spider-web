package com.kingsoft.spider.business.common.spider.tieba.pipeline;

import com.kingsoft.spider.business.common.spider.tieba.dto.tiebaDto;
import com.vdurmont.emoji.EmojiParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleData {

    void execute(String title, List<String> allContent, List<String> allRepeatTime, List<String> allAuthor, String time, List<tiebaDto> dtos) {
//        Pattern emojPattern = Pattern.compile("([D83cDc00-D83cDfff]|[D83dDc00-D83dDfff]|[2600-27ff]){0,}", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        for (int i = 0; i < allContent.size(); i++) {
            if (!allContent.get(i).trim().equals("")) {
                tiebaDto dto = new tiebaDto();
                dto.setText(EmojiParser.removeAllEmojis(allContent.get(i)));
                try {
                    if (allRepeatTime.size() > i) {
                        dto.setTime(dateFormat.parse(allRepeatTime.get(i)).getTime() / 1000);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dto.setTitle(EmojiParser.removeAllEmojis(title));

                if (allAuthor.size() > i) {
                    dto.setAuthor(EmojiParser.removeAllEmojis(allAuthor.get(i)));
                }
                //判断是否入库
                checkIsWriteDB(time, dtos, dto);
            }
        }
    }
    private void checkIsWriteDB(String time, List<tiebaDto> dtos, tiebaDto dto) {
        if (time == null||"".equals(time)) {
            dtos.add(dto);
        } else {
            Long date = Long.valueOf(time);
            if (date <dto.getTime())
            {
                dtos.add(dto);
            }
        }
    }
}