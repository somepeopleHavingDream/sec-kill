package org.yangxin.seckill.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yangxin.seckill.domain.SecKillUser;
import org.yangxin.seckill.result.Result;

/**
 * @author yangxin
 * 2023/4/8 16:17
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<SecKillUser> info(SecKillUser user) {
        log.info("info->{}", JSON.toJSONString(user));
        return Result.success(user);
    }
}
