package com.gmall.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    private JedisPool jedisPool;

    /**
     * 初始化连接池
     * @param host
     * @param port
     * @param database
     */
    public void initPool(String host, int port, int database) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxTotal(200);
        //核心连接数
        poolConfig.setMaxIdle(30);
        poolConfig.setBlockWhenExhausted(true);
        //  延迟时间
        poolConfig.setMaxWaitMillis(10 * 1000);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, host, port, 20 * 1000);

    }

    /**
     * 获取连接池对象
     * @return
     */
    public Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
