package mq;

import redis.clients.jedis.Jedis;

public class ListMQExample {
    public static void main(String[] args) throws InterruptedException {
        // 消费者
        new Thread(() -> consumer()).start();

        // 消费者（改良版：阻塞读）
        new Thread(() -> bConsumer()).start();

        // 生产者
        producer();
    }

    /**
     * 生产者
     */
    public static void producer() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 推送消息
        jedis.lpush("mq", "Hello, List.");
        Thread.sleep(1000);
        jedis.lpush("mq", "message 2.");
        Thread.sleep(2000);
        jedis.lpush("mq", "message 3.");
    }

    /**
     * 消费者
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 消费消息
        while (true) {
            // 获取消息
            String msg = jedis.rpop("mq");
            if (msg != null) {
                // 接收到了消息
                System.out.println("接收到消息：" + msg);
            }
        }
    }
    /**
     * 消费者（阻塞版）
     */
    public static void bConsumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        while (true) {
            // brpop() 第一个参数是超时时间，设置 0 表示一直阻塞下去
            for (String item : jedis.brpop(0,"mq")) {
                System.out.println(item);
            }
        }
    }
}

