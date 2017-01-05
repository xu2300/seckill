package org.seckill.dao;

import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by junweixu on 16/12/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//tell spring config
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
    //inject Dao dependency
    @Resource
    private SeckillDao seckillDao;


    @org.junit.Test
    public void testQueryById() throws Exception {
        long id =1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);

    }

    @org.junit.Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime );
        System.out.println("updateCount=" + updateCount);
    }

//Java does not save the formal parameters

    @org.junit.Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        for(Seckill seckill : seckills){
            System.out.println(seckill);
        }
    }

}