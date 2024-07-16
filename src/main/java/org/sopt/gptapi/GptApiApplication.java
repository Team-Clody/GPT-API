package org.sopt.gptapi;

import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsNameResolverBuilder;


import jakarta.annotation.PostConstruct;
import org.sopt.gptapi.config.PromptProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
@EnableConfigurationProperties(PromptProperty.class)
@AutoConfigureBefore({ DataSourceAutoConfiguration.class, SqlInitializationAutoConfiguration.class })
@EnableR2dbcAuditing
public class GptApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptApiApplication.class, args);
    }

}
