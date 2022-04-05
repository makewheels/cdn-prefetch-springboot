package com.github.makewheels.cdnprefetchspringboot;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CdnService {
    private void download(List<String> urlList) {
        for (String url : urlList) {
            System.out.println(url);
            HttpUtil.get(url);
        }
    }

    public JSONObject prefetch(JSONObject prefetch) {
        String missionId = prefetch.getString("missionId");
        List<String> urlList = prefetch.getJSONArray("urlList").toJavaList(String.class);
        download(urlList);
        JSONObject response = new JSONObject();
        response.put("missionId", missionId);
        return response;
    }
}
