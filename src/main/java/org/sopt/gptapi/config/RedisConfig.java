package org.sopt.gptapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.listener.RedisMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

  private final String EXPIRED_EVENT_PATTERN = "__keyevent@0__:expired";

  @Value("${spring.datasource.data.redis.host}")
  private String host;
  @Value("${spring.datasource.data.redis.port}")
  private int port;

  private final Executor commonTaskExecutor;
  private final ObjectMapper objectMapper;


  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  @Bean(name = "redisMessageTaskExecutor")
  public Executor redisMessageTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(1);
    threadPoolTaskExecutor.setMaxPoolSize(1);
    return threadPoolTaskExecutor;
  }

  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisConnectionFactory redisConnectionFactory,
      MessageListenerAdapter listenerAdapter, Executor commonTaskExecutor) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(redisConnectionFactory);
    container.addMessageListener(listenerAdapter, new PatternTopic(EXPIRED_EVENT_PATTERN));
    container.setTaskExecutor(commonTaskExecutor);
    return container;
  }

  @Bean
  public MessageListenerAdapter listenerAdapter(RedisMessageListener listener) {
    return new MessageListenerAdapter(listener);
  }

  @Bean
  @Primary
  public RedisTemplate<String, String> redisStringTemplate(
      RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setDefaultSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(connectionFactory);
    return redisTemplate;
  }

  @Bean
  public RedisTemplate<String, Object> redisObjectTemplate(
      RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    // 객체를 직렬화하는 방법 설정
    template.setValueSerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    template.setDefaultSerializer(new StringRedisSerializer());
    return template;
  }
}
