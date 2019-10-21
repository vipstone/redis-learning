import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import utils.Config;

/**
 * 连接客户端
 */
public class Clients {
    // Redis 基础连接
    private static RedisClient _RedisClient = RedisClient.create(Config.RedisConfig);

    public static void main(String[] args) {
        StatefulRedisConnection<String, String> connection = _RedisClient.connect();
        RedisStringCommands<String, String> sync = connection.sync();
        // 设置 K,V=language,Java
        sync.set("language", "Java");
        System.out.println(sync.get("language"));
    }
}
