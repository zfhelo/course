package org.gdpi.course.config;

/**
 * @author zhf
 */
//@Configuration
public class RedisConfig {
    //@Bean
    //public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    //    // 初始化redisCacheWriter
    //    RedisCacheWriter redisCacheWriter =
    //            RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    //    // 采用json序列化
    //    GenericJackson2JsonRedisSerializer
    //            jsonSerializer = new GenericJackson2JsonRedisSerializer();
    //
    //    RedisSerializationContext.SerializationPair<Object> pair =
    //            RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);
    //
    //    RedisCacheConfiguration redisCacheConfiguration =
    //            RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
    //    redisCacheConfiguration.entryTtl(Duration.ofDays(1));// 设置有效期
    //
    //    return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    //}
}
