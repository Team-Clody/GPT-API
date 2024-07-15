package org.sopt.gptapi.config;

import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig {

  private final Executor commonTaskExecutor;

  @Bean(name = "taskExecutor")
  public Executor taskExecutor() {
    return commonTaskExecutor;
  }
}
