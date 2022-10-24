package com.gj.crm.web.interceptor;

import com.gj.crm.commons.contants.Contants;
import com.gj.crm.settings.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 郭嘉
 * @date 2022/9/29 - 18:31
 */
public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*做登陆验证
        如何做：如果用户没用登录，则跳转到登录页面
        1.拿session对象
        2.
        */
        HttpSession session =request.getSession();
        User user=(User) session.getAttribute(Contants.SSESSION_USER_KEY);
        if(user==null){
            response.sendRedirect(request.getContextPath());//此处没有用springmvc返回，自己重定向需要加上项目名字，或者动态获取
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
