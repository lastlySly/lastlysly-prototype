package com.lastlysly;

import com.lastlysly.demo.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 11:18
 **/
@SpringBootTest
public class MyTest {
    @Autowired
    private HelloService helloService;

    @Test
    public void test1() {
        helloService.getHello("111");
    }
}
