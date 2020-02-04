package mq;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class PubSubExample {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个新线程作为消费者
        new Thread(() -> consumer()).start();

        // 主题订阅
        new Thread(() -> pConsumer()).start();

        // 暂停 0.5s 等待消费者初始化
        Thread.sleep(500);
        // 生产者发送消息
        producer();
    }

    /**
     * 生产者
     */
    public static void producer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 推送消息
        jedis.publish("channel", "Hello, channel.");
    }

    /**
     * 普通消费者
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 接收并处理消息
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                // 接收消息，业务处理
                System.out.println("频道 " + channel + " 收到消息：" + message);
            }
        }, "channel");
    }

    /**
     * 主题订阅
     */
    public static void pConsumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 主题订阅
        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                // 接收消息，业务处理
                System.out.println(pattern + " 主题 | 频道 " + channel + " 收到消息：" + message);
            }
        }, "channel*");
    }
}
