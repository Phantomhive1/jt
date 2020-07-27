package com.jt.aop;

import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Component
@Aspect
public class CacheAOP {
    @Autowired
    private JedisCluster jedisCluster;

//    @Autowired
//    private ShardedJedis shardedJedis;

//    @Autowired
//    private Jedis jedis;

    @SuppressWarnings("unchecked")
    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint jt, CacheFind cacheFind) {
        String key = cacheFind.key();
        String firstArg = jt.getArgs()[0].toString();
        key += "::"+firstArg;
        Object result = null;
        if (jedisCluster.exists(key)) {
            String json = jedisCluster.get(key);
            MethodSignature methodSignature = (MethodSignature) jt.getSignature();
            result = ObjectMapperUtil.toObject(json, methodSignature.getReturnType());
            System.out.println("aop查询redis缓存");
        } else {
            try {
                result = jt.proceed();
                System.out.println("AOP查询数据库获取返回值结果");
                String json = ObjectMapperUtil.toJSON(result);
                int seconds = cacheFind.seconds();
                if (seconds > 0)
                    jedisCluster.setex(key, seconds, json);
                else
                    jedisCluster.set(key, json);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            }
        }
        return result;
    }
}
