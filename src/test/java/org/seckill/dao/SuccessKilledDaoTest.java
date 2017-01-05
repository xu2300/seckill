package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by junweixu on 16/12/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//tell spring config
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
    @Resource
    private  SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone =13456493450L;
        int insert = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertcount=" + insert);

    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000L;
        long phone =13456493450L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}