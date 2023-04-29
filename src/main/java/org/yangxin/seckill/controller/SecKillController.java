package org.yangxin.seckill.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yangxin.seckill.domain.SecKillOrder;
import org.yangxin.seckill.domain.SecKillUser;
import org.yangxin.seckill.rabbitmq.MqSender;
import org.yangxin.seckill.rabbitmq.SecKillMessage;
import org.yangxin.seckill.redis.GoodKey;
import org.yangxin.seckill.redis.RedisService;
import org.yangxin.seckill.result.CodeMsg;
import org.yangxin.seckill.result.Result;
import org.yangxin.seckill.service.GoodService;
import org.yangxin.seckill.service.OrderService;
import org.yangxin.seckill.vo.GoodVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yangxin
 * 2023/4/29 20:13
 */
@Controller
@RequestMapping("/secKill")
@Slf4j
public class SecKillController implements InitializingBean {

    private final GoodService goodService;
    private final OrderService orderService;

    private final RedisService redisService;

    private final MqSender mqSender;

    private final Map<Long, Boolean> LOCAL_OVER_MAP = new HashMap<>();

    @Autowired
    public SecKillController(GoodService goodService, OrderService orderService, RedisService redisService, MqSender mqSender) {
        this.goodService = goodService;
        this.orderService = orderService;
        this.redisService = redisService;
        this.mqSender = mqSender;
    }

    @Override
    public void afterPropertiesSet() {
        List<GoodVO> goodVOList = goodService.listGoodsVo();
        if (CollectionUtils.isEmpty(goodVOList)) {
            return;
        }

        for (GoodVO goodVO : goodVOList) {
            Long goodVOId = goodVO.getId();
            redisService.set(GoodKey.getSecKillGoodStock, "" + goodVOId, goodVO.getStockCount());
            LOCAL_OVER_MAP.put(goodVOId, false);
        }
    }

    @RequestMapping(value = "/{path}/doSecKill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> secKill(SecKillUser user,
                                   @RequestParam("goodId") Long goodId) {
        log.info("secKill user->{}, goodId->{}", JSON.toJSONString(user), goodId);

        if (Objects.isNull(user)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 内存标记，减少redis访问
        boolean over = LOCAL_OVER_MAP.get(goodId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 判断是否已经秒杀到了
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodId(user.getId(), goodId);
        if (Objects.nonNull(order)) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        /*
            为什么要单独维护一个秒杀结束的标志？

            1 所有的秒杀相关的接口都要加上活动是否结束的标志，如果结束就直接返回，包括轮询的接口，防止一直轮询没法结束
            2 管理后台也可以手动的更改这个标志，防止出现活动开始以后就没法结束这种意外的发生
         */

        /*
            Redis中的库存如何与DB中的库存保持一致？

            Redis中的数量不是库存，它的作用仅仅是为了阻挡多余的请求透传到db，起到一个保护DB的作用。
            因为秒杀商品的数量是有限的，比如只有10个，让1万个请求去访问DB是没有意义的，因为最多只有10个请求会下单成功，
            剩余的9990个请求都是无效的，是可以不用去访问db而直接失败的。因此，这是一个伪问题，我们是不需要保持一致的。
         */

        /*
            但这里是有漏洞的。
            如果用户发起了两次秒杀请求，其中第一次秒杀请求入队但是还未处理，第二次的请求已经执行到这里，那么就会出现同一个用户重复扣库存的情况。
            这应该不是预期的情况。
         */

        // 预减库存
        long stock = redisService.decr(GoodKey.getSecKillGoodStock, "" + goodId);
        if (stock < 0) {
            LOCAL_OVER_MAP.put(goodId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 入队
        SecKillMessage secKillMessage = new SecKillMessage();
        secKillMessage.setUser(user);
        secKillMessage.setGoodId(goodId);
        mqSender.sendSecKillMessage(secKillMessage);
        // 排队中
        return Result.success(0);
    }
}
