package com.github.makewheels.cdnprefetchspringboot;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Currency;
import java.util.List;

@Service
@Slf4j
public class CdnService {
    private long download(List<String> urlList) {
        long totalSize = 0;
        for (String url : urlList) {
            log.info(url);
            byte[] bytes = HttpUtil.createGet(url).execute().bodyBytes();
            totalSize += bytes.length;
        }
        return totalSize;
    }

    public JSONObject prefetch(JSONObject prefetch) {
        String missionId = prefetch.getString("missionId");
        log.info("收到新任务：missionId = " + missionId);
        new Thread(() -> {
            //计时器
            long start = System.currentTimeMillis();
            List<String> urlList = prefetch.getJSONArray("urlList").toJavaList(String.class);
            long totalSize = download(urlList);
            long time = System.currentTimeMillis() - start;

            JSONObject callbackBody = new JSONObject();
            callbackBody.put("missionId", missionId);
            callbackBody.put("totalSize", totalSize);
            callbackBody.put("count", urlList.size());
            callbackBody.put("time", time);
            log.info("Finished, callbackBody = " + callbackBody.toJSONString());
            HttpUtil.post(prefetch.getString("callbackUrl"), callbackBody.toJSONString());
        }).start();
        JSONObject response = new JSONObject();
        response.put("missionId", missionId);
        response.put("message", "我收到任务了");
        return response;
    }

}
