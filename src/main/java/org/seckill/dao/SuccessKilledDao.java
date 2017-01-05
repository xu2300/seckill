package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by junweixu on 16/12/26.
 */
public interface SuccessKilledDao {
    /*insert the detail of buying,filter the repeating buying;
    insert rows.
     */

    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /*look up the successkilled
       , and the entity of the commodity entity
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
