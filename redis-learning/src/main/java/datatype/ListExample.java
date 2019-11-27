package datatype;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;

/**
 * List 示例
 */
public class ListExample {
    public static void main(String[] args) {
        final String REDISKEY = "list";
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        jedis.auth("xxx");
        // 在头部插入一个或多个元素
        Long lpushResult = jedis.lpush(REDISKEY, "Java", "Sql");
        System.out.println(lpushResult); // 输出：2
        // 获取第 0 个元素的值
        String idValue = jedis.lindex(REDISKEY, 0);
        System.out.println(idValue); // 输出：Sql
        // 查询指定区间的元素
        List<String> list = jedis.lrange(REDISKEY, 0, -1);
        System.out.println(list); // 输出：[Sql, Java]
        // 在元素 Java 前面添加 MySQL 元素
        jedis.linsert(REDISKEY, ListPosition.BEFORE, "Java", "MySQL");
        System.out.println(jedis.lrange(REDISKEY, 0, -1)); // 输出：[Sql, MySQL, Java]
        jedis.close();
    }
}
