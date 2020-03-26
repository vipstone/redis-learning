import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 集群示例
 */
public class ClusterExample {
    public static void main(String[] args) {
        // 集群节点信息
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 30001));
        nodes.add(new HostAndPort("127.0.0.1", 30002));
        nodes.add(new HostAndPort("127.0.0.1", 30003));
        nodes.add(new HostAndPort("127.0.0.1", 30004));
        nodes.add(new HostAndPort("127.0.0.1", 30005));
        nodes.add(new HostAndPort("127.0.0.1", 30006));
        // 创建集群连接
        JedisCluster jedisCluster = new JedisCluster(nodes,
                15000,      // 超时时间
                10);    // 最大尝试重连次数
        // 添加数据
        String setResult = jedisCluster.set("lang", "redis");
        // 输出结果
        System.out.println("添加：" + setResult);
        // 查询结果
        String getResult = jedisCluster.get("lang");
        // 输出结果
        System.out.println("查询：" + getResult);
    }
}
