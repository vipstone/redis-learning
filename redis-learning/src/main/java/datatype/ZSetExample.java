package datatype;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ZSet 示例
 */
public class ZSetExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.auth("pwd654321"); // redis 服务器密码
        Map<String, Double> map = new HashMap<>();
        map.put("小明", 80.5d);
        map.put("小红", 75d);
        map.put("老王", 85d);
        // 为有序集合(ZSet)添加元素
        jedis.zadd("grade", map);
        // 查询分数在 80 分到 100 分之间的人(包含 80 分和 100 分)
        Set<String> gradeSet = jedis.zrangeByScore("grade", 80, 100);
        System.out.println(gradeSet); // 输出：[小明, 老王]
        // 查询小红的排名(排名从 0 开始)
        System.out.println(jedis.zrank("grade", "小明")); // 输出：1
        // 从集合中移除老王
        jedis.zrem("grade", "老王");
        // 查询有序集合中的所有元素(根据排名从小到大)
        Set<String> range = jedis.zrange("grade", 0, -1);
        System.out.println(range); // 输出：[小红, 小明]
        // 查询有序集合中的所有元素(根据 score 从小到大)
        Set<String> rangeByScore = jedis.zrangeByScore("grade", 0, 100);
        System.out.println(rangeByScore);
    }
}
