package org.yangxin.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yangxin.seckill.domain.User;
import org.yangxin.seckill.rabbitmq.MqSender;
import org.yangxin.seckill.redis.RedisService;
import org.yangxin.seckill.redis.UserKey;
import org.yangxin.seckill.result.Result;
import org.yangxin.seckill.service.UserService;

/**
 * @author yangxin
 * 2022/6/2 0:10
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    private final UserService userService;
    private final RedisService redisService;
    private final MqSender mqSender;

    @Autowired
    public SampleController(UserService userService, RedisService redisService, MqSender mqSender) {
        this.userService = userService;
        this.redisService = redisService;
        this.mqSender = mqSender;
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model)  {
        model.addAttribute("name", "Alice");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("111111");
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }

    @RequestMapping("/mq")
    @ResponseBody
    public Result<?> mq() {
        mqSender.send("hello world");
        return Result.success("hello world");
    }
}
