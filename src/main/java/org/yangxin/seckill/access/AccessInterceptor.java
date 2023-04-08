package org.yangxin.seckill.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangxin
 * 2023/4/8 16:33
 */
@SuppressWarnings("NullableProblems")
@Service
@Slf4j
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            getUser(request, response);
        }

        return true;
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter("token");
        log.info("getUser paramToken->{}", paramToken);
    }
}