package org.yangxin.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yangxin.seckill.dao.GoodDao;

/**
 * @author yangxin
 * 2023/4/8 1:33
 */
@Service
public class GoodService {

    private final GoodDao goodDao;

    @Autowired
    public GoodService(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    public void listGoodsVo(){
        goodDao.listGoodsVo();
    }
}
