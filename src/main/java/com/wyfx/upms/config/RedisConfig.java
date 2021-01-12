package com.wyfx.upms.config;

/**
 * Author: liuxingyu
 * DATE: 2017-07-20.13:48
 * description:
 * version:
 * //
 */
//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.pool.max-wait}")
//    private long maxWaitMillis;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//
//    @Bean
//    public KeyGenerator wiselyKeyGenerator(){
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object o, Method method, Object... objects) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(o.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : objects){
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    @Bean
//    public JedisConnectionFactory redisConnectionFactory(){
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
//        factory.setTimeout(timeout);    //设置连接超时
//        //factory.setPassword(password);
//        return factory;
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate){
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        cacheManager.setDefaultExpiration(10);  //设置 key-value 超时时间
//        return cacheManager;
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//       // setSerializer(template);    //设置序列化工具，就不必实现Serializable接口
//        template.afterPropertiesSet();
//        template.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
//        return template;
//    }
//
//    private void setSerializer(StringRedisTemplate template){
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//    }
//}
