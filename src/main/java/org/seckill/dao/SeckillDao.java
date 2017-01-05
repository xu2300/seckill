package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by junweixu on 16/12/26.
 */
public interface SeckillDao {
    /**
     * decrease stock
     * @param seckillId
     * @param killTime
     * @return
     * if the influenced row > 1, show the influenced row number
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * base on the id to look up commodity
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * base on the offset to look up the commodity
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);


}
