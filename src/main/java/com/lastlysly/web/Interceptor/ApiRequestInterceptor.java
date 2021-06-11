package com.lastlysly.web.Interceptor;

import com.lastlysly.web.ApiRequestContext;
import com.lastlysly.web.view.CustomRequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-06-06 15:37
 **/
public class ApiRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        CustomRequestContext requestContext = ApiRequestContext.get();
        String userId = request.getParameter("userId");
        String adminCode = request.getParameter("adminCode");
        if (StringUtils.isNotBlank(userId)) {
            requestContext.setUserId(userId);
        }
        if (StringUtils.isNotBlank(adminCode)) {
            requestContext.setAdminCode(adminCode);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ApiRequestContext.destroyRequestContext();
    }
}
