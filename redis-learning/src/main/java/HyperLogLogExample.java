import redis.clients.jedis.Jedis;

public class HyperLogLogExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 添加元素
        jedis.pfadd("k", "redis", "sql");
        jedis.pfadd("k", "redis");
        // 统计元素
        long count = jedis.pfcount("k");
        // 打印统计元素
        System.out.println("k：" + count);
        // 合并 HLL
        jedis.pfmerge("k2", "k");
        // 打印新 HLL
        System.out.println("k2：" + jedis.pfcount("k2"));
    }
}
