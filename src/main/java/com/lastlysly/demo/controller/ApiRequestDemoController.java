package com.lastlysly.demo.controller;

import com.lastlysly.demo.view.UserInfoView;
import com.lastlysly.web.ApiRequestContext;
import com.lastlysly.web.view.CustomRequestContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-06-06 15:59
 **/
@RestController
@RequestMapping("/test")
public class ApiRequestDemoController {
    @GetMapping("/testReq")
    public String testReq(UserInfoView userInfoView) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        CustomRequestContext requestContext = ApiRequestContext.get();
        System.out.println("===========");
        System.out.println(userInfoView.getUserId() + "====" + requestContext.getUserId());
//        System.out.println(requestContext.getUserId());
        return userInfoView.getUserId() + "===" + requestContext.getUserId();
    }

    @GetMapping("/test1")
    public String test1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "test1";
    }
    @GetMapping("/test2")
    public String test2() {
        return "test1";
    }
}
