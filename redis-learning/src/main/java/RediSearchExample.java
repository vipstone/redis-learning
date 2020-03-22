import io.redisearch.client.AddOptions;
import io.redisearch.client.Client;
import io.redisearch.Document;
import io.redisearch.SearchResult;
import io.redisearch.Query;
import io.redisearch.Schema;


/**
 * RediSearch 示例代码
 */
public class RediSearchExample {
    public static void main(String[] args) {
        // 连接 Redis 服务器和指定索引
        Client client = new Client("myidx", "127.0.0.1", 6379);
        // 定义索引
        Schema schema = new Schema().addTextField("title",
                5.0).addTextField("desc", 1.0);
        // 删除索引
        client.dropIndex();
        // 创建索引
        client.createIndex(schema, Client.IndexOptions.Default());
        // 设置中文编码
        AddOptions addOptions = new AddOptions();
        addOptions.setLanguage("chinese");
        // 添加数据
        Document document = new Document("doc1");
        document.set("title", "天气预报");
        document.set("desc", "今天的天气很好，是个阳光明媚的大晴天，有蓝蓝的天空和白白的云朵。");
        // 向索引中添加文档
        client.addDocument(document,addOptions);
        // 查询
        Query q = new Query("天气") // 设置查询条件
                .setLanguage("chinese") // 设置为中文编码
                .limit(0,5);
        // 返回查询结果
        SearchResult res = client.search(q);
        // 输出查询结果
        System.out.println(res.docs);
    }
}
