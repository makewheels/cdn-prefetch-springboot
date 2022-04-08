package com.github.makewheels.cdnprefetchspringboot;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("cdn")
public class CdnController {
    @Resource
    private CdnService cdnService;

    @PostMapping("prefetch")
    public JSONObject prefetch(@RequestBody JSONObject prefetch) {
        return cdnService.prefetch(prefetch);
    }
}
