package com.github.makewheels.cdnprefetchspringboot;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.scripts.JO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CdnService {
    private void download(List<String> urlList) {
        for (String url : urlList) {
            log.info(url);
            HttpUtil.get(url);
        }
        log.info("Finished");
    }

    public JSONObject prefetch(JSONObject prefetch) {
        new Thread(() -> {
            String missionId = prefetch.getString("missionId");
            log.info("missionId = " + missionId);
            List<String> urlList = prefetch.getJSONArray("urlList").toJavaList(String.class);
            download(urlList);
        }).start();
        return new JSONObject();
    }
}
