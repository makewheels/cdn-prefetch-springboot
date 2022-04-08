package com.github.makewheels.cdnprefetchspringboot;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Currency;
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
        String missionId = prefetch.getString("missionId");
        log.info("收到新任务：missionId = " + missionId);
        new Thread(() -> {
            List<String> urlList = prefetch.getJSONArray("urlList").toJavaList(String.class);
            download(urlList);
        }).start();
        JSONObject response = new JSONObject();
        response.put("missionId", missionId);
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        System.out.println(cpuInfo);

        response.put("message", "我收到任务了,"
                + " mac = " + NetUtil.getLocalMacAddress()
                + "\ncpuInfo = " + OshiUtil.getCpuInfo().getCpuModel());
        return response;
    }

}
