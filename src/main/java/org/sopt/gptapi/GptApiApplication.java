package org.sopt.gptapi;

import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsNameResolverBuilder;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GptApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptApiApplication.class, args);
    }

}
