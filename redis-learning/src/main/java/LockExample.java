import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import utils.JedisUtils;

import java.util.Collections;


public class LockExample {
    static final String _LOCKKEY = "REDISLOCK"; // 锁 key
    static final String _FLAGID = "UUID:6379";  // 标识（UUID）
    static final Integer _TimeOut = 90;     // 最大超时时间

    public static void main(String[] args) {
        Jedis jedis = JedisUtils.getJedis();
        // 加锁
        boolean lockResult = lock(jedis, _LOCKKEY, _FLAGID, _TimeOut);
        // 逻辑业务处理
        if (lockResult) {
            System.out.println("加锁成功");
        } else {
            System.out.println("加锁失败");
        }
        // 手动释放锁
        if (unLock(jedis, _LOCKKEY, _FLAGID)) {
            System.out.println("锁释放成功");
        } else {
            System.out.println("锁释放成功");
        }
    }

    /**
     * @param jedis       Redis 客户端
     * @param key         锁名称
     * @param flagId      锁标识（锁值），用于标识锁的归属
     * @param secondsTime 最大超时时间
     * @return
     */
    public static boolean lock(Jedis jedis, String key, String flagId, Integer secondsTime) {
        SetParams params = new SetParams();
        params.ex(secondsTime); // 设置过期时间
        params.nx();
        String res = jedis.set(key, flagId, params);
        if (StringUtils.isNotBlank(res) && res.equals("OK"))
            return true;
        return false;
    }

    /**
     * 释放分布式锁
     * @param jedis   Redis 客户端
     * @param lockKey 锁的 key
     * @param flagId  锁归属标识
     * @return 是否释放成功
     */
    public static boolean unLock(Jedis jedis, String lockKey, String flagId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(flagId));
        if ("1L".equals(result)) { // 判断执行结果
            return true;
        }
        return false;
    }
}
