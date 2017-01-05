package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by junweixu on 16/12/27.
 */


/*
service interface: based on "USER"
1.
 */
public interface SeckillService {

    /*
    Search all the kill document
     */
    List<Seckill> getSeckillList();

    /*
    Search one the kill document
     */
    Seckill getById(long seckillId);

    /*
    when the seckill start ,output kill interface address.
    Or output system time and kill time.
     */
    Exposer exportSeckillUrl(long seckillId);


    //execute kill action
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)throws SeckillException,RepeatKillException,
            SeckillException;
}
