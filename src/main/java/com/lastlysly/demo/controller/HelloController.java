package com.lastlysly.demo.controller;

import com.lastlysly.annotations.SyncLockMethod;
import com.lastlysly.demo.service.HelloService;
import com.lastlysly.handler.exception.MyCustomException;
import com.lastlysly.uitls.LockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 14:19
 **/
@RestController
@RequestMapping("/test")
public class HelloController {

    private Map<String,String> joinUserIdsMap = new ConcurrentHashMap<>();
    @Autowired
    private HelloService helloService;
    @GetMapping("/hello")
    public String sayHello() {
        helloService.getHello("233");
        return "hello";
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/testLock/{userId}")
    public String testLock(@PathVariable("userId") String userId) {
        String lockValue = userId;
        String lockKey = UUID.randomUUID().toString();
        try{
            if (LockUtil.tryLock(lockValue,lockKey)) {
                if (joinUserIdsMap.containsKey(userId)){
                    System.out.println("用户" + userId + "已购买，不能重复购买。");
                    return "用户" + userId + "已购买，不能重复购买。";
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                joinUserIdsMap.put(userId,userId);
                System.out.println("用户" + userId + "购买成功");
                return "用户" + userId + "购买成功";
            }
            System.out.println("用户" + userId + "没有获取到锁");
            return "没有获取到锁";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LockUtil.unLock(lockValue,lockKey);
            System.out.println("用户" + userId + "进入finally，如果持有锁则解锁，解锁");
        }
        return null;
    }
    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/testLock2/{userId}")
    public String testLock2(@PathVariable("userId") String userId) {
        String lockValue = userId;
        String lockKey = UUID.randomUUID().toString();
        synchronized(lockValue.intern()) {
            if (joinUserIdsMap.containsKey(userId)){
                System.out.println("用户" + userId + "已购买，不能重复购买。");
                return "用户" + userId + "已购买，不能重复购买。";
            }
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            joinUserIdsMap.put(userId,userId);
            System.out.println("用户" + userId + "购买成功");
            return "用户" + userId + "购买成功";
        }
    }

    @GetMapping("/testLock3/{userId}")
    @SyncLockMethod(argsIndex = {0})
    public String testLock3(@PathVariable("userId") String userId) {
        if (joinUserIdsMap.containsKey(userId)){
            System.out.println("用户" + userId + "已购买，不能重复购买。");
            return "用户" + userId + "已购买，不能重复购买。";
        }
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        joinUserIdsMap.put(userId,userId);
        System.out.println("用户" + userId + "购买成功");
        return "用户" + userId + "购买成功";
    }

    @GetMapping("tet")
    public String tet() throws MyCustomException {
        throw new MyCustomException("testt");
    }
}
