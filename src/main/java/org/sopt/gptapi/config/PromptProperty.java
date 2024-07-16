package org.sopt.gptapi.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties(PromptProperty.class)
@PropertySource(value = "classpath:prompt.yml", factory = YamlPropertySourceFactory.class)
@Configuration
public class PromptProperty {

  private String prompt;

}
