package com.desklamp.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jedis客户端工具-集群配置模式
 * @author Joney
 * @date 2018/8/29 11:59
 */
@Slf4j
public class RedisClient {

	@Value("${redis.cluster.maxTotal}")
	private String maxTotal;

	@Value("${redis.cluster.maxIdle}")
	private String maxIdle;

	@Value("${redis.cluster.minIdle}")
	private String minIdle;

	@Value("${redis.cluster.blockWhenExhausted}")
	private String blockWhenExhausted;

	@Value("${redis.cluster.maxWaitMillis}")
	private String maxWaitMillis;

	@Value("${redis.cluster.testOnBorrow}")
	private String testOnBorrow;

	@Value("${redis.cluster.numTestsPerEvictionRun}")
	private String numTestsPerEvictionRun;

	@Value("${redis.cluster.minEvictableIdleTimeMillis}")
	private String minEvictableIdleTimeMillis;

	@Value("${redis.cluster.timeBetweenEvictionRunsMillis}")
	private String timeBetweenEvictionRunsMillis;

	@Value("${redis.cluster.hosts}")
	private String hosts;

	@Value("${redis.cluster.prefix}")
	private String prefix;

	/**
	 * 集群客户端
	 */
	private static JedisCluster jedis;
	/**
	 * 缓存前缀
	 */
	private static String cachePrefix = "default";

	public RedisClient() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(Integer.valueOf(maxTotal));
		poolConfig.setMaxIdle(Integer.valueOf(maxIdle));
		poolConfig.setMinIdle(Integer.valueOf(minIdle));
		poolConfig.setBlockWhenExhausted(Boolean.valueOf(blockWhenExhausted));
		poolConfig.setMaxWaitMillis(Long.valueOf(maxWaitMillis));
		poolConfig.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
		poolConfig.setNumTestsPerEvictionRun(Integer.valueOf(numTestsPerEvictionRun));
		poolConfig.setMinEvictableIdleTimeMillis(Long.valueOf(minEvictableIdleTimeMillis));
		poolConfig.setTimeBetweenEvictionRunsMillis(Long.valueOf(timeBetweenEvictionRunsMillis));

		String clusterHostsString = hosts;
		String[] redisClusterHosts = clusterHostsString.split(",");

		Set<HostAndPort> redisClusterNodes = new HashSet<>();
		for (String redisClusterHost : redisClusterHosts) {
			String[] hp = redisClusterHost.split(":");
			redisClusterNodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
		}
		String appCode = prefix;
		if (!StringUtils.isEmpty(appCode)) {
			cachePrefix = appCode;
		}
		if (jedis == null) {
			synchronized (RedisClient.class) {
				if (jedis == null) {
					jedis = new JedisCluster(redisClusterNodes, poolConfig);
				}

			}
		}
	}

	/**
	 * 获取Jedis实例
	 * @return JedisCluster
	 * @author by Joney on 2018/8/29 16:45
	 */
	public static JedisCluster getJedis() {
		return jedis;
	}

	/**
	 * 设置值
	 * @param key   键
	 * @param value 值
	 * @return void
	 * @author by Joney on 2018/8/29 14:55
	 */
	public static void set(String key, Object value) {
		set(key, value, true, -1);
	}

	/**
	 * 设置值
	 * @param key        键
	 * @param value      值
	 * @param expireTime 过期时间
	 * @return void
	 * @author by Joney on 2018/8/29 14:55
	 */
	public static void set(String key, Object value, int expireTime) {
		set(key, value, true, expireTime);
	}

	/**
	 * 设置值
	 * @param key               键
	 * @param value             值
	 * @param needJsonSerialize 需要序列化
	 * @return void
	 * @author by Joney on 2018/8/29 14:55
	 */
	public static void set(String key, Object value, boolean needJsonSerialize) {
		set(key, value, needJsonSerialize, -1);
	}

	/**
	 * 设置值
	 * @param key               键
	 * @param value             值
	 * @param needJsonSerialize 需要序列化
	 * @param expireTime        过期时间(秒)
	 * @return void
	 * @author by Joney on 2018/8/29 14:55
	 */
	public static void set(String key, Object value, boolean needJsonSerialize, int expireTime) {
		String putVal;
		if (needJsonSerialize) {
			putVal = JSONObject.toJSONString(value);
		} else {
			putVal = value.toString();
		}
		key  = cachePrefix + ":" + key;
		jedis.set(key, putVal);

		if (expireTime >= 0) {
			jedis.expire(key, expireTime);
		}
	}

	/**
	 * 获取值
	 * @param key 键
	 * @return String
	 * @author by Joney on 2018/8/29 15:01
	 */
	public static String get(String key) {
		return jedis.get(cachePrefix + ":" + key);
	}

	/**
	 * 获取并转换值
	 * @param key   键
	 * @param clazz 需要转换的类
	 * @return void
	 * @author by Joney on 2018/8/29 15:02
	 */
	public static <T> T get(String key, Class<T> clazz) {
		String value = jedis.get(cachePrefix + ":" + key);
		if (!StringUtils.isEmpty(value)) {
			return JSONObject.parseObject(value, clazz);
		}
		return null;
	}

	/**
	 * 设置hash键值映射
	 * @param key 键
	 * @param map 值
	 * @return void
	 * @author by Joney on 2018/8/29 15:15
	 */
	public static void putMap(String key, Map<String, String> map) {
		jedis.hmset(cachePrefix + ":" + key, map);
	}

	/**
	 * 设置hash键值映射，有过期时间
	 * @param key        键
	 * @param map        值
	 * @param expireTime 过期时间
	 * @return void
	 * @author by Joney on 2018/8/29 15:15
	 */
	public static void putMap(String key, Map<String, String> map, int expireTime) {
		jedis.hmset(cachePrefix + ":" + key, map);
		if (expireTime >= 0) {
			jedis.expire(key, expireTime);
		}
	}

	/**
	 * 获取hash键值映射
	 * @param key 键
	 * @return Map<String, String>
	 * @author by Joney on 2018/8/29 15:20
	 */
	public static Map<String, String> getMap(String key) {
		return jedis.hgetAll(cachePrefix + ":" + key);
	}

	/**
	 * 获取hash键值映射中某域对应的值集合
	 * @param key    键
	 * @param fields 域
	 * @return List<String>
	 * @author by Joney on 2018/8/29 15:20
	 */
	public static List<String> getMapValues(String key, String... fields) {
		return jedis.hmget(cachePrefix + ":" + key, fields);
	}

	/**
	 * 设置list值
	 * @param  key  键
	 * @param  list 集合
	 * @return void
	 * @author by Joney on 2018/8/29 15:43
	 */
	public static void putList(String key, List<String> list) {
		key = cachePrefix + ":" + key;
		if (!CollectionUtils.isEmpty(list)) {
			for (String t : list) {
				jedis.lpush(key, t);
			}
		}
	}

	/**
	 * 设置list值
	 * @param  key  键
	 * @param  list 集合
	 * @return void
	 * @author by Joney on 2018/8/29 15:43
	 */
	public static void putList(String key, List<String> list, int expireTime) {
		key = cachePrefix + ":" + key;
		if (!CollectionUtils.isEmpty(list)) {
			for (String t : list) {
				jedis.lpush(key, t);
			}
			if (expireTime >= 0) {
				jedis.expire(key, expireTime);
			}
		}
	}

	/**
	 * 获取list值
	 * @param key 键
	 * @return List<String>
	 * @author by Joney on 2018/8/29 15:48
	 */
	public static List<String> getList(String key) {
		return jedis.lrange(cachePrefix + ":" + key, 0 , -1);
	}

	/**
	 * 删除
	 * @param  key 键
	 * @return void
	 * @author by Joney on 2018/8/29 15:50
	 */
	public static void del(String key) {
		jedis.del(cachePrefix + ":" + key);
	}

    /*public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "Tom");
        hashMap.put("age", "20");
        hashMap.put("gender", "男");

        RedisClient.set("object", hashMap);
        System.out.println(RedisClient.get("object"));
		RedisClient.del("object");
        System.out.println(RedisClient.get("object"));

		RedisClient.putMap("map", hashMap);
        System.out.println(RedisClient.getMap("map"));
		RedisClient.del("map");
        System.out.println(RedisClient.getMap("map"));

		RedisClient.putList("list", Arrays.asList("apple", "orange", "banana"));
        System.out.println(RedisClient.getList("list"));
		RedisClient.del("list");
        System.out.println(RedisClient.getList("list"));
    }*/
}
