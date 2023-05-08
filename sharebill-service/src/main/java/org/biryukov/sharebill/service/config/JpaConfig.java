package org.biryukov.sharebill.service.config;

import org.biryukov.sharebill.core.config.JdbcConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(JdbcConfig.class)
@ComponentScan("org.biryukov.sharebill.service")
@EnableJpaRepositories({"org.biryukov.sharebill.service"})
@EntityScan({"org.biryukov.sharebill.service"})
//@EnableTransactionManagement
public class JpaConfig {

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return  new LocalContainerEntityManagerFactoryBean();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }*/
}
