package org.yangxin.seckill.rabbitmq;

import lombok.Data;
import org.yangxin.seckill.domain.SecKillUser;

/**
 * @author yangxin
 * 2023/4/29 21:12
 */
@Data
public class SecKillMessage {

    private SecKillUser user;

    private Long goodId;
}
