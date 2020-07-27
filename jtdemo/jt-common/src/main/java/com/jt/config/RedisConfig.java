package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    @Value("${redis.nodes}")
    private String redisNodes;

//    @Value("${redis.host}")
//    private String host;
//
//    @Value("${redis.port}")
//    private Integer port;

//    @Bean
//    public Jedis jedis() {
//        return new Jedis(host, port);
//    }

//    @Bean
//    public ShardedJedis shardedJedis() {
//        String[] nodes = redisNodes.split(",");
//        List<JedisShardInfo> list = new ArrayList<>();
//        for (String node : nodes) {
//            String host = node.split(":")[0];
//            int port = Integer.parseInt(node.split(":")[1]);
//            list.add(new JedisShardInfo(host, port));
//        }
//        return new ShardedJedis(list);
//
//    }

    @Bean
    public JedisCluster jedisCluster() {
        String[] clusters = redisNodes.split(",");
        Set<HostAndPort> nodeSet = new HashSet<>();
        for (String cluster : clusters) {
            String host = cluster.split(":")[0];
            int port = Integer.parseInt(cluster.split(":")[1]);
            nodeSet.add(new HostAndPort(host, port));
        }
        return new JedisCluster(nodeSet);

    }

}
