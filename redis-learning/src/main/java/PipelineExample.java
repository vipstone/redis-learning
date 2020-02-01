import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class PipelineExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.auth("pwd654321"); // redis 服务器密码
        // 记录执行开始时间
        long beginTime = System.currentTimeMillis();

        // 获取 Pipeline 对象
        Pipeline pipe = jedis.pipelined();
        for (int i = 0; i < 100; i++) {
            pipe.set("key" + i, "val" + i);
            pipe.del("key"+i);
        }
        // 执行命令
        pipe.sync();
        // 执行命令并返回结果
//        List<Object> res = pipe.syncAndReturnAll();
//        for (Object obj : res) {
//            System.out.println(obj);
//        }

//        // 普通操作方式
//        for (int i = 0; i < 100; i++) {
//            jedis.set("key" + i, "val" + i);
//            jedis.del("key"+i);
//        }

        // 记录执行结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时：" + (endTime - beginTime) + "毫秒");
    }
}
