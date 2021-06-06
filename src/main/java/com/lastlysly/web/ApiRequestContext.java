package com.lastlysly.web;

import com.lastlysly.web.view.CustomRequestContext;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-06-06 15:25
 **/
public class ApiRequestContext {
    private static ThreadLocal<CustomRequestContext> requestContextThreadLocal = new ThreadLocal<>();
    public static CustomRequestContext get() {
        CustomRequestContext requestContext = requestContextThreadLocal.get();
        if (requestContext == null) {
            System.out.println("创建");
            requestContext = new CustomRequestContext();
            requestContextThreadLocal.set(requestContext);
            return requestContext;
        }
        return requestContext;
    }

    public static void destroyRequestContext() {
        System.out.println("销毁");
        requestContextThreadLocal.remove();
    }
}
