package datatype;

import redis.clients.jedis.Jedis;

import java.util.List;

public class StringExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        jedis.auth("xxx");
        // 添加一个元素
        jedis.set("mystr", "redis");
        // 获取元素
        String myStr = jedis.get("mystr");
        System.out.println(myStr); // 输出：redis
        // 添加多个元素(key,value,key2,value2)
        jedis.mset("db", "redis", "lang", "java");
        // 获取多个元素
        List<String> mlist = jedis.mget("db", "lang");
        System.out.println(mlist);  // 输出：[redis, java]
        // 给元素追加字符串
        jedis.append("db", ",mysql");
        // 打印追加的字符串
        System.out.println(jedis.get("db")); // 输出：redis,mysql
        // 当 key 不存在时，赋值键值
        Long setnx = jedis.setnx("db", "db2");
        // 因为 db 元素已经存在，所以会返回 0 条修改
        System.out.println(setnx); // 输出：0
        // 字符串截取
        String range = jedis.getrange("db", 0, 2);
        System.out.println(range); // 输出：red
        // 添加键值并设置过期时间(单位：毫秒)
        String setex = jedis.setex("db", 1000, "redis");
        System.out.println(setex); // 输出：ok
        // 查询键值的过期时间
        Long ttl = jedis.ttl("db");
        System.out.println(ttl); // 输出：1000
    }
}
