package org.yangxin.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yangxin.seckill.service.GoodService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangxin
 * 2023/4/8 1:14
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodController {

    private final GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @RequestMapping(value="/to_list")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.info("list");
        goodService.listGoodsVo();
        return "ok";
    }
}
