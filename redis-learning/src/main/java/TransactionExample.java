import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 事务
 */
public class TransactionExample {
    public static void main(String[] args) {
        // 创建 Redis 连接
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        // 设置 Redis 密码
        jedis.auth("xxx");
        // 设置键值
        jedis.set("k", "v");
        // 开启监视 watch
        jedis.watch("k");
        // 开始事务
        Transaction tx = jedis.multi();
        // 命令入列
        tx.set("k", "v2");
        // 执行事务
        tx.exec();
        System.out.println(jedis.get("k"));
        jedis.close();
    }
}
