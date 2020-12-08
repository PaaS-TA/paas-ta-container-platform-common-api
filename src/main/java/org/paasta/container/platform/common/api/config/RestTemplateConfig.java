package org.paasta.container.platform.common.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Config 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.24
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Rest template rest template
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
