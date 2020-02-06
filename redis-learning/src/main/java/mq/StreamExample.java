package mq;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;
import utils.JedisUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamExample {
    public static void main(String[] args) throws InterruptedException {
        // 消费者
        new Thread(() -> consumer()).start();
        Thread.sleep(1000);
        // 生产者
        producer();
    }

    /**
     * 生产者
     */
    public static void producer() {
        Jedis jedis = JedisUtils.getJedis();
        // 推送消息
        Map<String, String> map = new HashMap<>();
        map.put("name", "redis");
        map.put("age", "10");
        // 添加消息
        StreamEntryID id = jedis.xadd("mq", null, map);
        System.out.println("消息添加成功 ID：" + id);
    }

    /**
     * 消费者
     */
    public static void consumer() {
        Jedis jedis = JedisUtils.getJedis();
        // 消费消息
        while (true) {
            // 获取消息，new StreamEntryID().LAST_ENTRY 标识获取当前时间以后的新增消息
            Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleImmutableEntry<>("mq",
                    new StreamEntryID().LAST_ENTRY);
            // 阻塞读取一条消息（最大阻塞时间120s）
            List<Map.Entry<String, List<StreamEntry>>> list = jedis.xread(1, 120 * 1000, entry);
            if (list.size() == 1) {
                // 读取到消息
                System.out.println("读取到消息 ID：" + list.get(0).getValue().get(0).getID());
                // 使用 Gson 来打印 JSON 格式的消息内容
                System.out.println("内容：" + new Gson().toJson(list.get(0).getValue().get(0).getFields()));
            }
        }
    }
}
