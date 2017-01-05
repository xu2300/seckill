package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by junweixu on 16/12/27.
 */

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //INJECTION SERVICE DEPENDENCE
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;
    //confused md5
    private  final String slat= "sawercawervr3gc23c3c3c323cc4";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //cache opotination
        //1. redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            //2.get from database
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false,seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime()
            || nowTime.getTime() >endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());

        }
        //convert specific string , not reverse
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private  String getMD5(long seckillId){
        String base = seckillId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    @Transactional
    /*annotation pro:
    1.Things to do as short as possible,
    do not intersperse other network operations
    2. not all the methods need transaction, when there is only one change operation,do not need transanction
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw  new SeckillException("seckill data rewrite");
        }

        //execute kill logic:decrease stock + document buying action
        Date nowTime = new Date();

        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if(updateCount <= 0){
                //not  updating document, kill ending
                throw new SeckillCloseException("seckill is closed");

            }else {
                //document kill action
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

                //unique:seckillId, UserPhone
                if(insertCount <=0){
                    throw  new RepeatKillException("seckill repeated");
                }
                else{
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }

            }
        }catch (SeckillCloseException e1){
            throw e1;
        }
        catch (RepeatKillException e2){
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getLocalizedMessage(),e);
            //convert all the complie exception runtime exception
            throw  new SeckillException("seckill inner error"+e.getMessage());
        }

    }
}
