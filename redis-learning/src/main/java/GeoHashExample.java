import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoHashExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Map<String, GeoCoordinate> map = new HashMap<>();
        // 添加小明的位置
        map.put("xiaoming", new GeoCoordinate(116.404269, 39.913164));
        // 添加小红的位置
        map.put("xiaohong", new GeoCoordinate(116.36, 39.922461));
        // 添加小美的位置
        map.put("xiaomei", new GeoCoordinate(116.499705, 39.874635));
        // 添加小二
        map.put("xiaoer", new GeoCoordinate(116.193275, 39.996348));
        jedis.geoadd("person", map);
        // 查询小明和小红的直线距离
        System.out.println("小明和小红相距：" + jedis.geodist("person", "xiaoming",
                "xiaohong", GeoUnit.KM) + " KM");
        // 查询小明附近 5 公里的人
        List<GeoRadiusResponse> res = jedis.georadiusByMemberReadonly("person", "xiaoming",
                5, GeoUnit.KM);
        for (int i = 1; i < res.size(); i++) {
            System.out.println("小明附近的人：" + res.get(i).getMemberByString());
        }
    }
}
