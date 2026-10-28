package br.com.exemplos.outbound.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "br.com.exemplos.outbound")
@EntityScan(basePackages = "br.com.exemplos.core.domain.entity")
@ComponentScan(basePackages = { "br.com.exemplos.outbound" })
public class JPAConfiguration {

}
