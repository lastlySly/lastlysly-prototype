package com.lastlysly.handler;

import com.lastlysly.handler.exception.MyCustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 15:46
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MyCustomException.class)
    @ResponseBody
    public Object customExceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception e) {
        System.out.println("全局异常处理中心：" + e.getMessage());
        return "全局异常处理中心：" + e.getMessage();
    }
}
