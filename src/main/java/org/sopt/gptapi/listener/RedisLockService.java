package org.sopt.gptapi.listener;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockService {

  private final StringRedisTemplate redisTemplate;

  public boolean tryLock(String key, long timeout, TimeUnit unit) {
    Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "locked", timeout, unit);
    return success != null && success;
  }

  public void unlock(String key) {
    redisTemplate.delete(key);
  }
}
