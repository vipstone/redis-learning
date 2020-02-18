import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Slowlog;
import utils.JedisUtils;

import java.util.List;

/**
 * 慢查询
 */
public class SlowExample {
    public static void main(String[] args) {
        Jedis jedis = JedisUtils.getJedis();
        // 插入慢查询（因为 slowlog-log-slower-than 设置为 0，所有命令都符合慢操作）
        jedis.set("db", "java");
        jedis.set("lang", "java");
        // 慢查询记录的条数
        long logLen = jedis.slowlogLen();
        // 所有慢查询
        List<Slowlog> list = jedis.slowlogGet();
        // 循环打印
        for (Slowlog item : list) {
            System.out.println("慢查询命令："+ item.getArgs()+
                    " 执行了："+item.getExecutionTime()+" 微秒");
        }
        // 清空慢查询日志
        jedis.slowlogReset();
    }
}
