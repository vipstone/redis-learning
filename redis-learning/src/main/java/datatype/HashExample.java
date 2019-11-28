package datatype;

import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 哈希表示例
 */
public class HashExample {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        jedis.auth("xxx");
        // 把 Key 值定义为变量
        final String REDISKEY = "myhash";
        // 插入单个元素
        jedis.hset(REDISKEY, "key1", "value1");
        // 查询单个元素
        Map<String, String> singleMap = jedis.hgetAll(REDISKEY);
        System.out.println(singleMap.get("key1"));  // 输出：value1
        // 查询所有元素
        Map<String, String> allMap = jedis.hgetAll(REDISKEY);
        System.out.println(allMap.get("k2")); // 输出：val2
        System.out.println(allMap); // 输出：{key1=value1, k1=val1, k2=val2, k3=9.2, k4=v4...}
        // 删除单个元素
        Long delResult = jedis.hdel(REDISKEY, "key1");
        System.out.println("删除结果：" + delResult);    // 输出：删除结果：1
        // 查询单个元素
        System.out.println(jedis.hget(REDISKEY, "key1")); // 输出：返回 null
    }
}
