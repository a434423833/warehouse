package com.weiyebancai.warehouse.filter;

import com.weiyebancai.warehouse.utile.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 主要过滤没有登录请求的
 */
@WebFilter(filterName = "overAllFilter", urlPatterns = {"/zhuti/*"})
public class OverAllFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OverAllFilter.class);

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String ip = IpUtil.getIpFromRequest((HttpServletRequest) request);
        HttpSession session = ((HttpServletRequest) request).getSession();
        String currentURL = ((HttpServletRequest) request).getRequestURI();
        Object object = session.getAttribute("user");
        if (object == null && !currentURL.endsWith("/login/index.html") && !currentURL.endsWith("userLogin")) {
            LOGGER.info(ip + "进入到全局过滤器,");
            ((HttpServletResponse) response).sendRedirect("redirect:/login/index.html");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}


