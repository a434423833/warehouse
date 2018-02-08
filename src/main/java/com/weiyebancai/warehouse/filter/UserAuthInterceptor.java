package com.weiyebancai.warehouse.filter;

import com.weiyebancai.warehouse.pojo.UserVO;
import com.weiyebancai.warehouse.utile.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 普通身份拦截器
 */
public class UserAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OverAllFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        //在请求处理之前进行调用,（Controller)方法调用之前, 只有返回true才会继续向下执行，返回false取消当前请求
        String ip = IpUtil.getIpFromRequest((HttpServletRequest) request);
        Object obj = request.getSession().getAttribute("user");
        if (obj == null) {
            LOGGER.error(ip + "进入到全局拦截器,被拦截，未登录");
            throw new RuntimeException("请先登录");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    }
}
