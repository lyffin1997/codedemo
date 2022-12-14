package com.lyffin.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //redis默认jdk序列化方式来序列化java对象，但是对象必须实现Serializable，否则如下手动序列化
        //User user = new User("黎峰", "3");
        //String jsonUser = new ObjectMapper().writeValueAsString(user);
        //配置具体的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //过期方法
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        /**
         * Jackson ObjectMapper 中的 enableDefaultTyping 方法由于安全原因，
         * 从 2.10.0 开始标记为过期，建议用 activateDefaultTyping 方法代替，
         * 所以如果继续使用 enableDefaultTyping 会有警告出现，我们现在要消除这个警告，
         * 去看一下 enableDefaultTyping 源码
         */
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //序列化String
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //key采用string的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key也采用string序列化
        template.setHashKeySerializer(stringRedisSerializer);
        //value的key也采用string序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的key的value也采用string序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


}
