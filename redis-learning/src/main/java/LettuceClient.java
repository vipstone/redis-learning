import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import utils.Config;

/**
 * Lettuce 客户端演示
 */
public class LettuceClient {

    public static void main(String[] args) {
        lettuceUse();
    }

    /**
     * Lettuce 使用演示
     */
    private static void lettuceUse() {
        // 创建 Redis 连接
        RedisClient redisClient = RedisClient.create(Config.REDISCONFIG_PROTOCOL);
        // 获取 Redis 连接
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 获取同步执行命令对象 RedisStringCommands
        RedisStringCommands<String, String> sync = connection.sync();
        // 设置缓存（有效期 10s）
        sync.set("language", "Java", new SetArgs().ex(10));
        // 获取缓存
        String getStr = sync.get("language");
        System.out.println(getStr);
    }

}
