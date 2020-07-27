package com.jt;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

public class TestRedisShards {

    @Test
    public void test01() {
        List<JedisShardInfo> shards = new ArrayList<>();
        shards.add(new JedisShardInfo("192.168.126.129", 6379));
        shards.add(new JedisShardInfo("192.168.126.129", 6380));
        shards.add(new JedisShardInfo("192.168.126.129", 6381));

        ShardedJedis shardedJedis = new ShardedJedis(shards);
        shardedJedis.set("shards", "准备分片操作！");
        System.out.println(shardedJedis.get("shards"));

    }
}
