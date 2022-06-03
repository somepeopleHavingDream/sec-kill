package org.yangxin.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yangxin.seckill.domain.User;
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

    @Autowired
    public SampleController(UserService userService) {
        this.userService = userService;
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
}
