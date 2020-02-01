import redis.clients.jedis.Jedis;

/**
 * Redis 过期时间操作相关示例
 */
public class TTLExample {
    public static void main(String[] args) throws InterruptedException {
        // 创建 Redis 连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 设置 Redis 密码
//        jedis.auth("xxx");
        // 存储键值对（默认情况下永不过期）
        jedis.set("k", "v");
        // 查询 TTL（过期时间）
        Long ttl = jedis.ttl("k");
        // output：过期时间：-1
        System.out.println("过期时间：" + ttl);
        // 设置 100s 后过期
        jedis.expire("k", 100);
        // 等待 1s 后执行
        Thread.sleep(1000);
        // output：执行 expire 后的 TTL=99
        System.out.println("执行 expire 后的 TTL=" + jedis.ttl("k"));
        // 设置 n 毫秒后过期
        jedis.pexpire("k", 100000);
        // 设置某个时间戳后过期（精确到秒）
        jedis.expireAt("k", 1573468990);
        // 设置某个时间戳后过期（精确到毫秒）
        jedis.pexpireAt("k", 1573468990000L);
        // 移除过期时间
        jedis.persist("k");
    }
}
