package org.devlos.practiceredisspringboot;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class controller {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Configuration
    public class RedisConfig {

        @Value("${spring.redis.host}")
        private String host;

        @Value("${spring.redis.port}")
        private int port;

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory(host, port);
        }
        @Bean
        public RedisTemplate<?, ?> redisTemplate() {
            RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            return redisTemplate;
        }
    }


    @GetMapping
    public void redisTest() {
        System.out.println("test");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("test", LocalDateTime.now().toString(), 20, TimeUnit.SECONDS);
    }
}
