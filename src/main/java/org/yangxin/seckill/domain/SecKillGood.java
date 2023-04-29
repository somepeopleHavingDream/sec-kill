package org.yangxin.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author yangxin
 * 2023/4/29 21:44
 */
@Data
public class SecKillGood {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
