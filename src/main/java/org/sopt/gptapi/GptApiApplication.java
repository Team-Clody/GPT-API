package org.sopt.gptapi;

import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsNameResolverBuilder;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;

@SpringBootApplication
//@EnableR2dbcRepositories(basePackages = "org.sopt.gptapi.domain")
@AutoConfigureBefore({ DataSourceAutoConfiguration.class, SqlInitializationAutoConfiguration.class })
public class GptApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptApiApplication.class, args);
    }

}
