import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import utils.Config;

import java.util.HashSet;
import java.util.Set;

/**
 * 哨兵演示
 */
public class SentinelExample {
    // master name
    private static String _MASTER_NAME = "mymaster";

    public static void main(String[] args) {
        // Sentinel 配置信息
        Set<String> set = new HashSet<>();
        // 连接信息 ip:port
        set.add("127.0.0.1:26379");
        // 创建 Sentinel 连接池
        JedisSentinelPool jedisSentinel = new JedisSentinelPool(_MASTER_NAME,
                set, Config.REDIS_AUTH);
        // 获取 Redis 客户端
        Jedis jedis = jedisSentinel.getResource();
        // 设置元素
        String setRes = jedis.set("key", "Hello, redis.");
        System.out.println(setRes);
        // 获取元素
        System.out.println(jedis.get("key"));
    }
}
