package com.lastlysly.demo.service;

import com.lastlysly.annotations.SyncLockMethod;
import org.springframework.stereotype.Service;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 15:58
 **/
@Service
public class HelloService {

    @SyncLockMethod(argsIndex = {0})
    public Object getHello(String userId) {
        System.out.println("业务输出");
        return "hello";
    }
}
