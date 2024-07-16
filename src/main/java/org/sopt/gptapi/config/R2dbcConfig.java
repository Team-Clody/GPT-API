package org.sopt.gptapi.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

  @Value("${spring.r2dbc.url}")
  private String host;

  @Value("${spring.r2dbc.username}")
  private String username;

  @Value("${spring.r2dbc.password}")
  private String password;

  @Override
  @Bean
  public ConnectionFactory connectionFactory() {
    return ConnectionFactories.get(ConnectionFactoryOptions.builder()
        .option(ConnectionFactoryOptions.DRIVER, "pool")
        .option(ConnectionFactoryOptions.PROTOCOL, "postgresql")
        .option(ConnectionFactoryOptions.HOST, host)
        .option(ConnectionFactoryOptions.PORT, 3306)
        .option(ConnectionFactoryOptions.USER, username)
        .option(ConnectionFactoryOptions.PASSWORD, password)
        .option(ConnectionFactoryOptions.DATABASE, "clody")
        .option(Option.valueOf("initialSize"), 10)
        .option(Option.valueOf("maxSize"), 20)
        .option(Option.valueOf("maxIdleTime"), Duration.ofMinutes(5))
        .option(Option.valueOf("maxLifeTime"), Duration.ofMinutes(5))
        .build());
  }
}
