package org.yangxin.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author yangxin
 * 2023/4/29 21:42
 */
@Data
public class OrderInfo {

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
