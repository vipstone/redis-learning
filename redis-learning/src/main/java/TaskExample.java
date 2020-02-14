import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import utils.JedisUtils;

/**
 * 定时任务
 */
public class TaskExample {
    public static final String _TOPIC = "__keyevent@0__:expired"; // 订阅频道名称
    public static void main(String[] args) {
        Jedis jedis = JedisUtils.getJedis();
        // 执行定时任务
        doTask(jedis);
    }

    /**
     * 订阅过期消息，执行定时任务
     * @param jedis Redis 客户端
     */
    public static void doTask(Jedis jedis) {
        // 订阅过期消息
        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                // 接收到消息，执行定时任务
                System.out.println("收到消息：" + message);
            }
        }, _TOPIC);
    }
}
