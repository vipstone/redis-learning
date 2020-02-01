package datatype;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.List;

/**
 * 字符串示例
 */
public class StringExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.auth("pwd654321"); // redis 服务器密码
        // 添加一个元素
        jedis.set("mystr", "redis");
        // 获取元素
        String myStr = jedis.get("mystr");
        System.out.println(myStr); // 输出：redis
        // 添加多个元素(key,value,key2,value2)
        jedis.mset("db", "redis", "lang", "java");
        // 获取多个元素
        List<String> mlist = jedis.mget("db", "lang");
        System.out.println(mlist);  // 输出：[redis, java]
        // 给元素追加字符串
        jedis.append("db", ",mysql");
        // 打印追加的字符串
        System.out.println(jedis.get("db")); // 输出：redis,mysql
        // 当 key 不存在时，赋值键值
        Long setnx = jedis.setnx("db", "db2");
        // 因为 db 元素已经存在，所以会返回 0 条修改
        System.out.println(setnx); // 输出：0
        // 字符串截取
        String range = jedis.getrange("db", 0, 2);
        System.out.println(range); // 输出：red
        // 添加键值并设置过期时间(单位：毫秒)
        String setex = jedis.setex("db", 1000, "redis");
        System.out.println(setex); // 输出：ok
        // 查询键值的过期时间
        Long ttl = jedis.ttl("db");
        System.out.println(ttl); // 输出：1000
        // JSON 示例
        Gson gson = new Gson();
        // 初始化用户数据
        User user = new User();
        user.setId(1);
        user.setName("Redis");
        user.setAge(10);
        String jsonUser = gson.toJson(user);
        // 打印用户信息(json)
        System.out.println(jsonUser); // 输出：{"id":1,"name":"Redis","age":10}
        // 把字符串存入 Redis
        jedis.set("user_1", jsonUser);
        // 从 Redis 中获取用户信息
        String getUserData = jedis.get("user_1");
        User userData = gson.fromJson(getUserData, User.class);
        System.out.println(userData.getId() + ":" + userData.getName()); // 输出结果：1:Redis
    }

    /**
     * 用户对象类
     */
    static class User implements Serializable {
        private int id; // 编号
        private String name; // 姓名
        private int age; // 年龄
        // 其他信息...

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
