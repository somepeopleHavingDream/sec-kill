package org.yangxin.seckill.access;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yangxin.seckill.redis.AccessKey;
import org.yangxin.seckill.redis.RedisService;
import org.yangxin.seckill.result.CodeMsg;
import org.yangxin.seckill.result.Result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author yangxin
 * 2023/4/8 16:33
 */
@SuppressWarnings("NullableProblems")
@Service
@Slf4j
public class AccessInterceptor implements HandlerInterceptor {

    private final RedisService redisService;

    @Autowired
    public AccessInterceptor(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod) {
            getUser(request, response);

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (Objects.isNull(accessLimit)) {
                return true;
            }

            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String key = request.getRequestURI();

            AccessKey accessKey = AccessKey.withExpire(seconds);
            Integer count = redisService.get(accessKey, key, Integer.class);
            if (Objects.isNull(count)) {
                redisService.set(accessKey, key, 1);
            } else if (count < maxCount) {
                redisService.incr(accessKey, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }

        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        String str = JSON.toJSONString(Result.error(codeMsg));

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(str.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter("token");
        log.info("getUser paramToken->{}", paramToken);
    }
}