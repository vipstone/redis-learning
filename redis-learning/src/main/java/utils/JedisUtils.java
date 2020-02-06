package utils;

import redis.clients.jedis.Jedis;

/**
 * Jedis 工具类
 */
public class JedisUtils {
    /**
     * 创建 Jedis 对象
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
        jedis.auth(Config.REDIS_AUTH);
        return jedis;
    }
}
