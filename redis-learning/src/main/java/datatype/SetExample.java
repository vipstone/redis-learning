package datatype;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Set 示例集合
 */
public class SetExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.auth("pwd654321"); // redis 服务器密码
        // 创建集合并添加元素
        jedis.sadd("set1", "java", "golang");
        // 查询集合中的所有元素
        Set<String> members = jedis.smembers("set1");
        System.out.println(members); // 输出：[java, golang]
        // 查询集合中的元素数量
        System.out.println(jedis.scard("set1"));
        // 移除集合中的一个元素
        jedis.srem("set1", "golang");
        System.out.println(jedis.smembers("set1")); // 输出：[java]
        // 创建集合 set2 并添加元素
        jedis.sadd("set2", "java", "golang");
        // 查询两个集合中交集
        Set<String> inters = jedis.sinter("set1", "set2");
        System.out.println(inters); // 输出：[java]
        // 查询两个集合中并集
        Set<String> unions = jedis.sunion("set1", "set2");
        System.out.println(unions); // 输出：[java,golang]
        // 查询两个集合的错集
        Set<String> diffs = jedis.sdiff("set2", "set1");
        System.out.println(diffs); // 输出：[golang]
    }
}
