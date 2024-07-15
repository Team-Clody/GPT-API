package org.sopt.gptapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class YmlPropertyUtil {

  private final Environment environment;

  public String getProperty(String key) {
    return environment.getProperty(key);
  }
}
