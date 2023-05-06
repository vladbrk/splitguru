package org.biryukov.sharebill.service.config;

import org.biryukov.sharebill.core.config.JdbcConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(JdbcConfig.class)
@ComponentScan("org.biryukov.sharebill.service")
@EnableJpaRepositories({"org.biryukov.sharebill.service"})
@EntityScan({"org.biryukov.sharebill.service"})
public class JpaConfig {
}
