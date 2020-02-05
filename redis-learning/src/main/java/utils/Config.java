package utils;

/**
 * 自定义 Redis 配置文件
 */
public class Config {
    public static final String REDIS_HOST = "127.0.0.1";    // Redis Host
    public static final String REDIS_PORT = "6379";         // Redis Port
    public static final String REDIS_AUTH = "pwd654321";    // Redis Password
    // 包含 Redis 协议的连接字符串（Lettuce 使用）
    public static final String REDISCONFIG_PROTOCOL = "redis://pwd654321@127.0.0.1:6379";
}
