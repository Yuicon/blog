package com.yuicon.blogserver.config;

import com.yuicon.blogserver.model.Article;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Yuicon
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory lettuceConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

    @Bean(name = "reactiveRedisTemplate")
    public ReactiveRedisTemplate<String, Article> reactiveRedisTemplate(
            @Qualifier("lettuceConnectionFactory") ReactiveRedisConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<Article> serializer = new Jackson2JsonRedisSerializer<>(Article.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Article> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Article> serializationContext = builder
                .value(serializer)
                .hashValue(serializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
//
//    @Bean(name = "reactiveJsonPornographicPicturesRedisTemplate")
//    public ReactiveRedisTemplate<String, PornographicPictures> reactiveJsonPornographicPicturesRedisTemplate(
//            @Qualifier("lettuceConnectionFactory") ReactiveRedisConnectionFactory connectionFactory) {
//        Jackson2JsonRedisSerializer<PornographicPictures> serializer =
//                new Jackson2JsonRedisSerializer<>(PornographicPictures.class);
//
//        RedisSerializationContext.RedisSerializationContextBuilder<String, PornographicPictures> builder =
//                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, PornographicPictures> serializationContext = builder
//                .value(serializer)
//                .hashValue(serializer)
//                .build();
//        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
//    }

}
