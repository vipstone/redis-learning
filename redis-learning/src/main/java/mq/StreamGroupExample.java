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

/**
 * Stream 分组示例演示
 */
public class StreamGroupExample {
    private static final String _STREAM_KEY = "mq"; // 流 key
    private static final String _GROUP_NAME = "g1"; // 分组名称
    private static final String _CONSUMER_NAME = "c1"; // 消费者 1 的名称
    private static final String _CONSUMER2_NAME = "c2"; // 消费者 2 的名称

    public static void main(String[] args) {
        // 生产者
        producer();
        // 创建消费组
        createGroup(_STREAM_KEY, _GROUP_NAME);
        // 消费者 1
        new Thread(() -> consumer()).start();
        // 消费者 2
        new Thread(() -> consumer2()).start();
    }

    /**
     * 创建消费分组
     * @param stream    流 key
     * @param groupName 分组名称
     */
    public static void createGroup(String stream, String groupName) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.xgroupCreate(stream, groupName, new StreamEntryID(), true);
    }

    /**
     * 生产者
     */
    public static void producer() {
        Jedis jedis = JedisUtils.getJedis();
        // 添加消息 1
        Map<String, String> map = new HashMap<>();
        map.put("data", "redis");
        StreamEntryID id = jedis.xadd(_STREAM_KEY, null, map);
        System.out.println("消息添加成功 ID：" + id);
        // 添加消息 2
        Map<String, String> map2 = new HashMap<>();
        map2.put("data", "java");
        StreamEntryID id2 = jedis.xadd(_STREAM_KEY, null, map2);
        System.out.println("消息添加成功 ID：" + id2);
    }

    /**
     * 消费者 1
     */
    public static void consumer() {
        Jedis jedis = JedisUtils.getJedis();
        // 消费消息
        while (true) {
            // 读取消息
            Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleImmutableEntry<>(_STREAM_KEY,
                    new StreamEntryID().UNRECEIVED_ENTRY);
            // 阻塞读取一条消息（最大阻塞时间120s）
            List<Map.Entry<String, List<StreamEntry>>> list = jedis.xreadGroup(_GROUP_NAME, _CONSUMER_NAME, 1,
                    120 * 1000, true, entry);
            if (list != null && list.size() == 1) {
                // 读取到消息
                Map<String, String> content = list.get(0).getValue().get(0).getFields(); // 消息内容
                System.out.println("Consumer 1 读取到消息 ID：" + list.get(0).getValue().get(0).getID() +
                        " 内容：" + new Gson().toJson(content));
            }
        }
    }

    /**
     * 消费者 2
     */
    public static void consumer2() {
        Jedis jedis = JedisUtils.getJedis();
        // 消费消息
        while (true) {
            // 读取消息
            Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleImmutableEntry<>(_STREAM_KEY,
                    new StreamEntryID().UNRECEIVED_ENTRY);
            // 阻塞读取一条消息（最大阻塞时间120s）
            List<Map.Entry<String, List<StreamEntry>>> list = jedis.xreadGroup(_GROUP_NAME, _CONSUMER2_NAME, 1,
                    120 * 1000, true, entry);
            if (list != null && list.size() == 1) {
                // 读取到消息
                Map<String, String> content = list.get(0).getValue().get(0).getFields(); // 消息内容
                System.out.println("Consumer 2 读取到消息 ID：" + list.get(0).getValue().get(0).getID() +
                        " 内容：" + new Gson().toJson(content));
            }
        }
    }
}
