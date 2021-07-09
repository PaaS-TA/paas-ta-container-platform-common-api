package org.paasta.container.platform.common.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.paasta.container.platform"})
public class PaasTaContainerPlatformCommonApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasTaContainerPlatformCommonApiApplication.class, args);
    }

}
