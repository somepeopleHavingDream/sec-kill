package org.yangxin.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangxin
 * 2022/6/2 0:10
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model)  {
        model.addAttribute("name", "Alice");
        return "hello";
    }
}
